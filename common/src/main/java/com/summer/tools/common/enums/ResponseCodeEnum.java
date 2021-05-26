package com.summer.tools.common.enums;

import lombok.Getter;

/**
 * @author john
 */
@Getter
public enum ResponseCodeEnum {
    // 通用返回code
    /**
     * 系统内部异常
     */
    SYSTEM_ERROR(500, "Internal Server Error"),
    /**
     * 请求成功
     */
    SUCCESS(200, "OK"),
    /**
     * token错误
     */
    TOKEN_ERROR(600, "token error"),

    // 自定义业务返回code
    BAD_MODEL(400, "model check failed");

    ResponseCodeEnum(Integer  code, String message) {
        this.code = code;
        this.message = message;
    }

    private final Integer  code;
    private final String message;
}
