package org.minbox.framework.api.boot.cbrc.stateverify.common.model;

import lombok.Data;
import org.minbox.framework.api.boot.cbrc.stateverify.common.enums.ResponseCode;

import java.io.Serializable;

/**
 * 统一接口响应实体
 *
 * @author 恒宇少年
 */
@Data
public class ApiResponse<T> implements Serializable {
    /**
     * 响应数据内容
     */
    private T data;
    /**
     * 响应码
     * {@link ResponseCode}
     */
    private ResponseCode responseCode;

    private String code;
    /**
     * 当遇到错误时的异常提示内容
     * 格式化后的业务逻辑异常信息
     */
    private String msg;

    private ApiResponse() {
    }

    /**
     * 构建空的统一响应对象
     *
     * @return {@link ApiResponse}
     */
    public static ApiResponse builder() {
        return new ApiResponse();
    }

    /**
     * 执行成功的响应
     *
     * @return {@link ApiResponse}
     */
    public static ApiResponse success() {
        ApiResponse response = builder();
        response.setResponseCode(ResponseCode.SUCCESS);
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMsg(ResponseCode.SUCCESS.getMessage());
        return response;
    }

    public static ApiResponse cbrc_success() {
        ApiResponse response = builder();
        response.setResponseCode(ResponseCode.CBRC_SUCCESS);
        response.setCode(ResponseCode.CBRC_SUCCESS.getCode());
        response.setMsg(ResponseCode.CBRC_SUCCESS.getMessage());
        return response;
    }

    /**
     * 响应结果字段赋值
     *
     * @param data {@link #data}
     * @return {@link ApiResponse}
     */
    public ApiResponse<T> data(T data) {
        this.data = data;
        return this;
    }

    /**
     * 设置响应码
     *
     * @param responseCode {@link #code}
     * @return {@link ApiResponse}
     */
    public ApiResponse code(ResponseCode responseCode) {
        this.responseCode = responseCode;
        this.msg = responseCode.getMessage();
        this.code = responseCode.getCode();
        return this;
    }

    /**
     * 设置错误信息
     *
     * @param errorMsg {@link #msg}
     * @return {@link ApiResponse}
     */
    public ApiResponse errorMsg(String errorMsg) {
        this.msg = errorMsg;
        return this;
    }

    /**
     * 执行异常的响应
     *
     * @param responseCode {@link ResponseCode}
     * @return {@link ApiResponse}
     */
    public static ApiResponse error(ResponseCode responseCode) {
        ApiResponse response = builder();
        response.setResponseCode(responseCode);
        response.setCode(responseCode.getCode());
        return response;
    }


}
