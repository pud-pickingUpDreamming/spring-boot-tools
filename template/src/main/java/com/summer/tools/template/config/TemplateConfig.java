package com.summer.tools.template.config;

import com.summer.tools.template.properties.TemplateProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;

@Configuration
public class TemplateConfig {

    @Resource
    private TemplateProperties templateProperties;

    @Bean
    @ConditionalOnProperty(name = "template.enable", havingValue = "true")
    public void template() {
        System.out.println(templateProperties.getEnable());
    }
}