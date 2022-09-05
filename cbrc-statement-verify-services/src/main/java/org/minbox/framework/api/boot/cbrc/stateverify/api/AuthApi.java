package org.minbox.framework.api.boot.cbrc.stateverify.api;



import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.minbox.framework.api.boot.cbrc.stateverify.api.base.BaseApi;
import org.minbox.framework.api.boot.cbrc.stateverify.common.exception.LogicException;
import org.minbox.framework.api.boot.cbrc.stateverify.common.utils.OkHttpUtils;
import org.minbox.framework.api.boot.cbrc.stateverify.entity.AppInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;

/**
 * 系统用户接口定义
 *
 * @author 恒宇少年
 */
@RestController
@RequestMapping(value = "/cbrc/auth")
@Api(tags = "授权获取api")
public class AuthApi extends BaseApi {

    private OkHttpClient client = new OkHttpClient();



    @PostMapping (value = "/getAccessToken")
    @ApiOperation(value = "获取授权")
    public String getAccessToken(AppInfo appInfo) throws LogicException, Exception {
        String res = "";
        String url = "http://localhost:11000/oauth/token";
        // ?grant_type=password&username=xiongfei&password=xiongfei
        res = OkHttpUtils.builder().url(url)
                .addHeader("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString((appInfo.getClientId() + ":" + appInfo.getClientSecret()).getBytes()))
                // 有参数的话添加参数，可多个
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addParam("username", appInfo.getUsername())
                .addParam("password", appInfo.getPassword())
                .addParam("grant_type", "password")
                .post(false).sync();
        return res;

    }

    @PostMapping (value = "/refreshToken")
    @ApiOperation(value = "刷新授权")
    public String refreshToken(@RequestBody AppInfo appInfo) throws LogicException, Exception {
        String res = "";
        String url = "http://localhost:11000/oauth/token?grant_type=refresh_token";
        url=url+"&access_token="+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBpIl0sImV4cCI6MTY2MjM4NDMyNSwidXNlcl9uYW1lIjoieGlvbmdmZWkiLCJqdGkiOiJoZEF6dW9SNzJCUU5NcnF4Tld3cFM2UEczMDgiLCJjbGllbnRfaWQiOiJhcGktYm9vdC1hZG1pbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.2YOso_YhpoUa8ZAcVUkQ8qkUpFlQjUI4QXJfDSOLonk";
        url=url+"&refresh_token="+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBpIl0sInVzZXJfbmFtZSI6Inhpb25nZmVpIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6Im9oeDhRR05jZnFRMm1EV2Y5eWpyX1hzZDV0QSIsImV4cCI6MTY2MjQ0MDk0NiwianRpIjoiQUU1dTlrckpxSTJXZnpvcl9BZWZERjQ1X21BIiwiY2xpZW50X2lkIjoiYXBpLWJvb3QtYWRtaW4ifQ.weVOkStWlr989KZmUXr32gcFjGTV4Ebi3Nhd8PJvt1A";
        res = OkHttpUtils.builder().url(url)
                .addHeader("Content-Type", "application/json")
                .post(false).sync();
        return res;

    }

    public static void main(String[] args) throws Exception {
        String s = new AuthApi().getAccessToken(new AppInfo().setUsername("xiongfei").setPassword("xiongfei").setClientId("api-boot-admin").setClientSecret("123456"));
        System.err.println(s);
    }
}
