package com.summer.tools.webservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "webservices")
public class WebserviceProperties {
    // 是否启用开关
    private Boolean enable;
    // 用户认证相关
    public static final String HEADER_AUTH = "authority";
    public static final String HEADER_TOKEN = "token";
    private String username;
    private String passwd;
    // 客户端配置
    private Client client;

    @Getter
    @Setter
    public static class Client {
        /**
         * wsdl资源路径,如果有多个加属性区分,
         * 最好不要放在一个List里,区分度不高,后面用着累
         */
        private String uri;
    }
}
