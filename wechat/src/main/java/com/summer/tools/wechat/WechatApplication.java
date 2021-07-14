package com.summer.tools.wechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.summer.tools.wechat.orm.dao"})
@ComponentScan(basePackages = {"com.summer.tools.wechat", "com.summer.tools.common", "com.summer.tools.cache"})
public class WechatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatApplication.class, args);
    }

}
