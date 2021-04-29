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
public class ResponseUtil<T> {
    private Integer  code;
    private String message;
    private T data;

    public static final ResponseUtil<?> ERROR = new ResponseUtil<>(ResponseCodeEnum.SYSTEM_ERROR);
    public static final ResponseUtil<?> SUCCESS = new ResponseUtil<>(ResponseCodeEnum.SUCCESS);

    public ResponseUtil(HttpStatus httpStatus, T data){
        this.code=httpStatus.value();
        this.message=httpStatus.getReasonPhrase();
        this.data = data;
    }

    public ResponseUtil(ResponseCodeEnum responseCodeEnum){
        this.code = responseCodeEnum.getCode();
        this.message = responseCodeEnum.getMessage();
    }

    public ResponseUtil(ResponseCodeEnum responseCodeEnum, T data){
        this.code = responseCodeEnum.getCode();
        this.message = responseCodeEnum.getMessage();
        this.data = data;
    }

    public static <T> ResponseUtil<T> failure(HttpStatus httpStatus, T message){
        return new ResponseUtil<>(httpStatus, message);
    }

    public static <T> ResponseUtil<T> failure(ResponseCodeEnum responseCodeEnum, T message){
        return new ResponseUtil<>(responseCodeEnum, message);
    }

    public static <T> ResponseUtil<T> success(T result){
    	return new ResponseUtil<>(HttpStatus.OK, result);
    }

    public static <T> ResponseUtil<Page<T>> success(Integer total, List<T> data){
        Page<T> page = new Page<>(total, data);
        return new ResponseUtil<>(ResponseCodeEnum.SUCCESS, page);
    }

}
