package com.summer.tools.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BackendOperation {
    /**
     * 所属模块
     */
    String module() default "";
    /**
     * 具体操作
     */
    String function() default "";
    String value() default "";
}
