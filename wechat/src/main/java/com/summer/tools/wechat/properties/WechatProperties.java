package com.summer.tools.wechat.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "wechat")
public class WechatProperties {
    private String corpId;
    private String corpSecret;
}
