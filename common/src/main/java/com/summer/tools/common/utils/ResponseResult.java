package com.summer.tools.common.utils;

import com.summer.tools.common.enums.ResponseCodeEnum;
import com.summer.tools.common.model.Page;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * 返回工具类
 * @author john.wang
 */
@Setter
@Getter
@Accessors(chain = true)
@ToString
@NoArgsConstructor
public class ResponseResult<T> {
    private Integer  code;
    private String message;
    private T data;

    public static final ResponseResult<?> ERROR = new ResponseResult<>(ResponseCodeEnum.SYSTEM_ERROR);
    public static final ResponseResult<?> SUCCESS = new ResponseResult<>(ResponseCodeEnum.SUCCESS);

    public ResponseResult(HttpStatus httpStatus, T data){
        this.code=httpStatus.value();
        this.message=httpStatus.getReasonPhrase();
        this.data = data;
    }

    public ResponseResult(ResponseCodeEnum responseCodeEnum){
        this.code = responseCodeEnum.getCode();
        this.message = responseCodeEnum.getMessage();
    }

    public ResponseResult(ResponseCodeEnum responseCodeEnum, T data){
        this.code = responseCodeEnum.getCode();
        this.message = responseCodeEnum.getMessage();
        this.data = data;
    }

    public static <T> ResponseResult<T> failure(HttpStatus httpStatus, T message){
        return new ResponseResult<>(httpStatus, message);
    }

    public static <T> ResponseResult<T> failure(ResponseCodeEnum responseCodeEnum, T message){
        return new ResponseResult<>(responseCodeEnum, message);
    }

    public static <T> ResponseResult<T> success(T result){
    	return new ResponseResult<>(HttpStatus.OK, result);
    }

    public static <T> ResponseResult<Page<T>> success(Integer total, List<T> data){
        Page<T> page = new Page<>(total, data);
        return new ResponseResult<>(ResponseCodeEnum.SUCCESS, page);
    }

}
