package org.minbox.framework.api.boot.cbrc.stateverify.common.enums;

import lombok.Getter;

/**
 * 异常码
 *
 * @author 恒宇少年
 */
@Getter
public enum ResponseCode {
    SUCCESS("SUCCESS", "请求成功."),
    NO_USER_LOGIN("NO_USER_LOGIN", "未发现有用户登录"),
    USER_NOT_FOUND("USER_NOT_FOUND", "用户：%s，不存在."),
    USER_ALREADY_CREATED("USER_ALREADY_CREATED", "用户：%s，已经存在."),
    USER_STATUS_ABNORMAL("USER_STATUS_ABNORMAL", "用户：%s，状态异常."),
    PARAMETER_VALID_FAIL("PARAMETER_VALID_FAIL", "参数异常."),
    ACCOUNT_NOT_FOUND("ACCOUNT_NOT_FOUND", "账号：%s，在我行系统不存在."),

    CBRC_TOKEN_VALIDFAIL("CBRC_TOKEN_VALIDFAIL", "银监token:%s, 异常. %s"),
    CBRC_SUCCESS("0", "请求成功."),
    CBRC_FAIL("1","请求异常, %s")
    ;
    private String code;
    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
