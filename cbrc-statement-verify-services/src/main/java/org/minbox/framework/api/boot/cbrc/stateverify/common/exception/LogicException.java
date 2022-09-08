package org.minbox.framework.api.boot.cbrc.stateverify.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.minbox.framework.api.boot.cbrc.stateverify.common.enums.ResponseCode;

/**
 * 自定义业务逻辑异常
 *
 * @author 恒宇少年
 */
@NoArgsConstructor
public class LogicException extends RuntimeException {
    /**
     * 系统遇到业务逻辑异常时会通过@ControllerAdvice以及@ExceptionHandler
     * 自动化响应给调用者，使用详情参考博客文章：http://blog.yuqiyu.com/springboot-exception-handler-advice.html
     */
    @Getter
    private ResponseCode responseCode;

    @Getter
    private String code;
    /**
     * 业务异常的占位符参数集合
     * 如："用户：%s，已被禁用"，这里"%s"即为占位符，对应{@link #parameters} 索引值
     */
    private String[] parameters;
    /**
     * 格式化后的错误信息
     * {@link String#format(String, Object...)}
     */
    @Getter
    private String errorMessage;

    public LogicException(ResponseCode responseCode, String... parameters) {
        super(String.format(responseCode.getMessage(), parameters));
        this.responseCode = responseCode;
        this.code = responseCode.getCode();
        this.parameters = parameters;
        this.errorMessage = String.format(responseCode.getMessage(), this.parameters);
    }
}
