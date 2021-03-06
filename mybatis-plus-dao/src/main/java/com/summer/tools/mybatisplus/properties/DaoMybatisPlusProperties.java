package com.summer.tools.mybatisplus.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "tenant")
public class DaoMybatisPlusProperties {
    private Boolean enable;
    private String service;
}
