package com.summer.tools.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.springboot.template.orm.dao"})
public class MybatisPlusDaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusDaoApplication.class, args);
    }

}
