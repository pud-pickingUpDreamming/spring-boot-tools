package com.summer.tools.common.utils;

import com.summer.tools.common.exceptions.ValidException;

import java.util.Collection;

public class Assert {

    public static void notEmpty(Collection<?> collection) {
        if (null == collection || collection.size() < 1) {
            throw new ValidException(ValidException.ValidEnum.NOT_EMPTY);
        }
    }

    public static void empty(Collection<?> collection) {
        if (null != collection && collection.size() > 0) {
            throw new ValidException(ValidException.ValidEnum.EMPTY);
        }
    }

    public static<T> void equals(T self, T another) {

        if (self == null) {
            throw new ValidException((ValidException.ValidEnum.EQUAL));
        }

        if (self instanceof String) {
            if (!self.equals(another)) {
                throw new ValidException((ValidException.ValidEnum.EQUAL));
            }
        }
    }


}
