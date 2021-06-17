package com.summer.tools.common.handler;

import com.summer.tools.common.enums.ResponseCodeEnum;
import com.summer.tools.common.exceptions.BusinessException;
import com.summer.tools.common.utils.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.UnexpectedTypeException;
import javax.validation.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ValidationException.class, UnexpectedTypeException.class})
    public ResponseResult<String> exceptionHandle(ValidationException ex) {
        return ResponseResult.failure(HttpStatus.BAD_REQUEST.value(), shortMessage(ex.getMessage()));
    }

    @ExceptionHandler({NestedRuntimeException.class, HttpMessageNotReadableException.class})
    public ResponseResult<String> exceptionHandle(NestedRuntimeException ex) {
        return ResponseResult.failure(HttpStatus.BAD_REQUEST.value(), shortMessage(ex.getMessage()));
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ResponseResult<String> exceptionHandle(BindException ex) {
        return ResponseResult.failure(HttpStatus.BAD_REQUEST.value(), shortMessage(ex.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseResult<String> exceptionHandle(BusinessException ex) {
        return ResponseResult.failure(ex.getErr());
    }

    @ExceptionHandler({RuntimeException.class, IllegalStateException.class})
    public ResponseResult<String> exceptionHandle(RuntimeException ex) {
        return ResponseResult.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), shortMessage(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult<String> exceptionHandle(Exception ex) {
        return ResponseResult.failure(ResponseCodeEnum.SYSTEM_ERROR.getCode(), shortMessage(ex.getMessage()));
    }

    private static String shortMessage(String message) {
        if (StringUtils.isBlank(message)) {
            return message;
        }
        String[] messages = message.split(";");
        String tmpMessage = messages[messages.length - 1];

        if (tmpMessage.contains("[") && tmpMessage.contains("]")) {
            return tmpMessage.substring(tmpMessage.indexOf("[") + 1, tmpMessage.indexOf("]"));
        }
        return tmpMessage;
    }
}
