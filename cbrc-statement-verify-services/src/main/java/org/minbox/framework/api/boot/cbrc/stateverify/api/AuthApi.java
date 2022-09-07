package org.minbox.framework.api.boot.cbrc.stateverify.api;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.minbox.framework.api.boot.cbrc.stateverify.api.base.BaseApi;
import org.minbox.framework.api.boot.cbrc.stateverify.common.exception.LogicException;
import org.minbox.framework.api.boot.cbrc.stateverify.common.utils.OkHttpUtils;
import org.minbox.framework.api.boot.cbrc.stateverify.entity.AppInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @PostMapping (value = "/getAccessToken")
    @ApiOperation(value = "获取授权")
    public String getAccessToken(@Valid @RequestBody AppInfo appInfo) throws LogicException, Exception {
        String res = "";
        String url = "http://localhost:11000/oauth/token";
        res = OkHttpUtils.builder().url(url)
                .addHeader("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString(("api-boot-admin" + ":" + "123456").getBytes()))
                // 有参数的话添加参数，可多个
                .addParam("username", appInfo.getUsername())
                .addParam("password", appInfo.getPassword())
                .addParam("grant_type", "password")
                .post(false,true).sync();
        return res;

    }

    @PostMapping (value = "/refreshToken")
    @ApiOperation(value = "刷新授权")
    public String refreshToken(@Valid @RequestBody AppInfo appInfo) throws LogicException, Exception {
        String res = "";
        String url = "http://localhost:11000/oauth/token";
        res = OkHttpUtils.builder().url(url)
                .addHeader("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString(("api-boot-admin" + ":" + "123456").getBytes()))
                .addParam("refresh_token", appInfo.getRefreshToken())
                .addParam("grant_type", "refresh_token")
                .post(false,true).sync();
        return res;

    }

    public static void main(String[] args) throws Exception {
        String s = new AuthApi().getAccessToken(new AppInfo().setUsername("xiongfei").setPassword("xiongfei").setClientId("api-boot-admin").setClientSecret("123456"));
        System.err.println(s);
    }
}
