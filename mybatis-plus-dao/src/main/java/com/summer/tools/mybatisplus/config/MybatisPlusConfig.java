package com.summer.tools.mybatisplus.config;

import com.summer.tools.mybatisplus.properties.TemplateProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;

@Configuration
public class MybatisPlusConfig {

    @Resource
    private TemplateProperties templateProperties;

    @Bean
    @ConditionalOnProperty(name = "template.enable", havingValue = "true")
    public void template() {
        System.out.println(templateProperties.getEnable());
    }
}