package com.summer.tools.common.exceptions;

import com.summer.tools.common.enums.ResponseCodeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException{

    public BusinessException(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public BusinessException(ResponseCodeEnum err) {
        super();
        this.code = err.getCode();
        this.message = err.getMessage();
    }

    private int code;

    private String message;
}
