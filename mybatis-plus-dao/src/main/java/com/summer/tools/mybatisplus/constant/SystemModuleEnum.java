package com.summer.tools.mybatisplus.constant;

import lombok.Getter;

@Getter
public enum  SystemModuleEnum {

    DAO("dao"),
    COMMON("common"),
    FLOWABLE("flowable"),
    WEBSERVICE("webservice");

    SystemModuleEnum(String value) {
        this.value = value;
    }

    private String value;

    @SuppressWarnings("unused")
    public static boolean isValid(String value) {
        for(SystemModuleEnum moduleEnum:SystemModuleEnum.values()) {
            if (moduleEnum.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
