package com.summer.tools.common.validation;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.lang.reflect.Method;

/**
 * 枚举校验注解
 * 需要校验的枚举必须有value字段
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValid.EnumValidator.class})
public @interface EnumValid {

    String message() default "{custom.value.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> clazz();

    String method() default "isValid";

    class EnumValidator implements ConstraintValidator<EnumValid, Object> {

        private Class<? extends Enum<?>> clazz;
        private String validMethod;

        @Override
        public void initialize(EnumValid constraintAnnotation) {
            this.clazz = constraintAnnotation.clazz();
            this.validMethod = constraintAnnotation.method();
        }

        @SneakyThrows
        @Override
        public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

            // 如果没传值算校验通过,也可以根据需求调整为校验不通过
            if (ObjectUtils.isEmpty(value)) {
                return Boolean.TRUE;
            }

            if (clazz == null || validMethod == null) {
                return Boolean.TRUE;
            }

            // 传值的时候才校验值在不在枚举范围内
            Method method = clazz.getMethod(validMethod, value.getClass());
            return (boolean) method.invoke(null, value);
        }
    }
}
