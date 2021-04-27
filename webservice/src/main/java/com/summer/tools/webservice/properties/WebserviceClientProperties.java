package com.summer.tools.webservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "webservices.client")
public class WebserviceClientProperties {
    private Boolean enable;
    private String uri;
}
