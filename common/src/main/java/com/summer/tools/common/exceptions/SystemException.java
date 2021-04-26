package com.summer.tools.common.exceptions;

public class SystemException extends RuntimeException{

    public SystemException(int code, Exception e) {
        super(String.valueOf(code), e);
    }
}
