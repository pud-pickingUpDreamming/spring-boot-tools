package com.summer.tools.common.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidException extends RuntimeException{

    public ValidException(ValidEnum validEnum) {
        this.code = validEnum.code;
        this.message = validEnum.message;
    }

    private int code;
    private String message;

    public enum ValidEnum{
        NOT_EMPTY(1, "data required"),
        EQUAL(2, "data not equal");

        ValidEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        private final int code;
        private final String message;
    }
}
