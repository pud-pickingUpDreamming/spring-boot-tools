package com.summer.tools.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * swagger 配置
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "common.swagger")
public class SwaggerProperties {
    private String basePackage;
    private String description;
    private List<GlobalParam> globalParams;

    @Getter
    @Setter
    public static class GlobalParam {
        private String name;
        private String paramType = "header";
        private String javaType = "string";
        private String description;
    }
}
