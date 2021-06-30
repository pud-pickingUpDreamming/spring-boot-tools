package com.summer.tools.mybatisplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@ComponentScan(basePackages = {"com.summer.tools.mybatisplus", "com.summer.tools.common"})
public class MybatisPlusDaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusDaoApplication.class, args);
    }

    @EventListener(WebServerInitializedEvent.class)
    public void onWebServerReady(WebServerInitializedEvent event) {
        WebServer webServer = event.getApplicationContext().getWebServer();
        System.out.println(String.format("web服务:[%s], 端口:[%d]", webServer.getClass().getSimpleName(), webServer.getPort()));
    }

}
