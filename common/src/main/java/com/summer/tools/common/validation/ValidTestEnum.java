package com.summer.tools.common.validation;

import lombok.Getter;

@Getter
public enum ValidTestEnum {
    ADD(1, "添加"),
    EDIT(2, "编辑");

    ValidTestEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    private final Integer value;
    private final String name;

    /**
     * 用于枚举参数校验
     */
    @SuppressWarnings("unused")
    public static boolean isValid(Integer value) {
        for(ValidTestEnum testEnum:ValidTestEnum.values()) {
            if (testEnum.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
