package org.minbox.framework.api.boot.cbrc.stateverify.common.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.minbox.framework.api.boot.cbrc.stateverify.Interceptor.LoggingInterceptor;
import org.minbox.framework.api.boot.cbrc.stateverify.common.exception.LogicException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author: xiongfei
 * @date: 2022/09/05 18:27
 */
public class OkHttpUtils {

    private static volatile OkHttpClient okHttpClient = null;
    //用于异步请求时，控制访问线程数，返回结果,设置为0表示只能1个线程同时访问，用于async()方法调用
    private static volatile Semaphore semaphore = new Semaphore(0);
    private Map<String, String> headerMap;
    private Map<String, String> paramMap;
    private String url;
    private Request.Builder request;

    /**
     * 初始化okHttpClient，并且允许https访问
     */
    private OkHttpUtils() {
        if (okHttpClient == null) {
            synchronized (OkHttpUtils.class) {
                if (okHttpClient == null) {
                    TrustManager[] trustManagers = buildTrustManagers();
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .sslSocketFactory(createSSLSocketFactory(trustManagers), (X509TrustManager) trustManagers[0])
                            .hostnameVerifier((hostName, session) -> true)
                            .retryOnConnectionFailure(true)
                            .connectionPool(new ConnectionPool(10, 10, TimeUnit.SECONDS))
                            .addInterceptor(new LoggingInterceptor())
                            .build();
                    // addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36");
                }
            }
        }
    }

    /**
     * 创建OkHttpUtils
     *
     * @return
     */
    public static OkHttpUtils builder() {
        return new OkHttpUtils();
    }

    /**
     * 生成安全套接字工厂，用于https请求的证书跳过
     *
     * @return
     */
    private static SSLSocketFactory createSSLSocketFactory(TrustManager[] trustAllCerts) {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }

    private static TrustManager[] buildTrustManagers() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
    }

    /**
     * 添加url
     *
     * @param url
     * @return
     */
    public OkHttpUtils url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 添加参数
     *
     * @param key   参数名
     * @param value 参数值
     * @return
     */
    public OkHttpUtils addParam(String key, String value) {
        if (paramMap == null) {
            paramMap = new LinkedHashMap<>(16);
        }
        paramMap.put(key, value);
        return this;
    }

    /**
     * 添加请求头
     *
     * @param key   参数名
     * @param value 参数值
     * @return
     */
    public OkHttpUtils addHeader(String key, String value) {
        if (headerMap == null) {
            headerMap = new LinkedHashMap<>(16);
        }
        headerMap.put(key, value);
        return this;
    }

    /**
     * 初始化get方法
     *
     * @return
     */
    public OkHttpUtils get() {
        request = new Request.Builder().get();
        StringBuilder urlBuilder = new StringBuilder(url);
        if (paramMap != null) {
            urlBuilder.append("?");
            try {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    urlBuilder.append(URLEncoder.encode(entry.getKey(), "utf-8")).
                            append("=").
                            append(URLEncoder.encode(entry.getValue(), "utf-8")).
                            append("&");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
        request.url(urlBuilder.toString());
        return this;
    }

    /**
     * 初始化post方法
     *
     * @param isJsonPost true等于json的方式提交数据，类似postman里post方法的raw
     *                   false等于普通的表单提交
     * @return
     */
    public OkHttpUtils post(boolean isJsonPost, boolean isFormData) {
        RequestBody requestBody;
        if (isJsonPost) {
            String json = "";
            if (paramMap != null) {
                json = JSON.toJSONString(paramMap);
            }
            requestBody = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        } else if (isFormData) {//Content-Type: multipart/form-data

            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);

            if (paramMap != null) {
                paramMap.forEach(builder::addFormDataPart);
            }
            requestBody = builder.build();

        } else { //Content-Type:application/x-www-form-urlencoded
            FormBody.Builder formBody = new FormBody.Builder();
            if (paramMap != null) {
                paramMap.forEach(formBody::add);
            }
            requestBody = formBody.build();

        }
        request = new Request.Builder().post(requestBody).url(url);
        return this;
    }

    /**
     * 同步请求
     *
     * @return
     */
    public String sync() throws LogicException {
        setHeader(request);
        try {
            Response response = okHttpClient.newCall(request.build()).execute();
            assert response.body() != null;
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "请求失败：" + e.getMessage();
        }
    }

    /**
     * 异步请求，有返回值,因为使用锁，相当于同步请求
     */
    public String async() throws Exception {
        StringBuilder buffer = new StringBuilder("");
        setHeader(request);
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                semaphore.release();
                buffer.append("请求出错：").append(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                semaphore.release();
                assert response.body() != null;
                buffer.append(response.body().string());
            }
        });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 异步请求，带有接口回调
     *
     * @param callback
     */
    public void async(Callback callback) throws Exception {
        setHeader(request);
        okHttpClient.newCall(request.build()).enqueue(callback);
    }

    /**
     * 为request添加请求头
     *
     * @param request
     */
    private void setHeader(Request.Builder request) throws LogicException {
        if (headerMap != null) {
            try {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * 自定义一个接口回调
     */
    public interface ICallBack {

        void onSuccessful(Call call, String data);

        void onFailure(Call call, String errorMsg);

    }
}
