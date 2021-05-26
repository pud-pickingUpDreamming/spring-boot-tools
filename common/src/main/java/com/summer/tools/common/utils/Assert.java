package com.summer.tools.common.utils;

import com.summer.tools.common.enums.ResponseCodeEnum;
import com.summer.tools.common.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.Map;

/**
 * 断言工具类,只为消灭if-else
 */
@SuppressWarnings("unused")
public class Assert {

    public static<T> void notEmpty(T obj, ResponseCodeEnum response) {
        if (obj == null) {
            throw new BusinessException(response.getCode(), response.getMessage());
        }

        if (obj instanceof Collection && ((Collection<?>) obj).size() < 1) {
            throw new BusinessException(response.getCode(), response.getMessage());
        } else if (obj instanceof String && "".equals(((String) obj).trim())) {
            throw new BusinessException(response.getCode(), response.getMessage());
        } else if (obj instanceof Map && ((Map<?,?>) obj).size() < 1) {
            throw new BusinessException(response.getCode(), response.getMessage());
        }
    }

    public static<T> void notEmpty(T obj, HttpStatus response) {
        if (obj == null) {
            throw new BusinessException(response.value(), response.getReasonPhrase());
        }

        if (obj instanceof Collection && ((Collection<?>) obj).size() < 1) {
            throw new BusinessException(response.value(), response.getReasonPhrase());
        } else if (obj instanceof String && "".equals(((String) obj).trim())) {
            throw new BusinessException(response.value(), response.getReasonPhrase());
        } else if (obj instanceof Map && ((Map<?,?>) obj).size() < 1) {
            throw new BusinessException(response.value(), response.getReasonPhrase());
        }
    }

    public static<T> void empty(T obj, ResponseCodeEnum response) {
        if (obj == null) {
            return;
        }

        if (obj instanceof Collection && ((Collection<?>)obj).size() > 0) {
            throw new BusinessException(response.getCode(), response.getMessage());
        } else if (obj instanceof String && !"".equals(((String) obj).trim())) {
            throw new BusinessException(response.getCode(), response.getMessage());
        } else if (obj instanceof Map && ((Map<?,?>)obj).size() > 0) {
            throw new BusinessException(response.getCode(), response.getMessage());
        }
    }

    public static<T> void empty(T obj, HttpStatus response) {
        if (obj == null) {
            return;
        }

        if (obj instanceof Collection && ((Collection<?>)obj).size() > 0) {
            throw new BusinessException(response.value(), response.getReasonPhrase());
        } else if (obj instanceof String && !"".equals(((String) obj).trim())) {
            throw new BusinessException(response.value(), response.getReasonPhrase());
        } else if (obj instanceof Map && ((Map<?,?>)obj).size() > 0) {
            throw new BusinessException(response.value(), response.getReasonPhrase());
        }
    }

    public static void notNull(Object obj, ResponseCodeEnum response) {
        if (null == obj) {
            throw new BusinessException(response.getCode(), response.getMessage());
        }
    }

    public static void notNull(Object obj, HttpStatus response) {
        if (null == obj) {
            throw new BusinessException(response.value(), response.getReasonPhrase());
        }
    }

    public static<T> void equals(T self, T another, ResponseCodeEnum response) {

        if (self == null) {
            throw new BusinessException(response.getCode(), response.getMessage());
        }

        if (!self.equals(another)) {
            throw new BusinessException(response.getCode(), response.getMessage());
        }
    }

    public static<T> void equals(T self, T another, HttpStatus response) {

        if (self == null) {
            throw new BusinessException(response.value(), response.getReasonPhrase());
        }

        if (!self.equals(another)) {
            throw new BusinessException(response.value(), response.getReasonPhrase());
        }
    }

    public static void isTrue(boolean expression, ResponseCodeEnum response) {
        if (!expression) {
            throw new BusinessException(response.getCode(), response.getMessage());
        }
    }

    public static void isTrue(boolean expression, HttpStatus response) {
        if (!expression) {
            throw new BusinessException(response.value(), response.getReasonPhrase());
        }
    }


}
