package com.summer.tools.mybatisplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.summer.tools.mybatisplus", "com.summer.tools.common"})
public class MybatisPlusDaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusDaoApplication.class, args);
    }

}
