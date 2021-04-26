package com.summer.tools.common.config;

import com.summer.tools.common.properties.CommonProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;

@Configuration
public class CommonConfig {

    @Resource
    private CommonProperties commonProperties;

    @Bean
    @ConditionalOnProperty(name = "common.enable", havingValue = "true")
    public void template() {
        System.out.println(commonProperties.getEnable());
    }
}