package com.summer.tools.common.properties;

import com.summer.tools.common.utils.AesUtil;
import com.summer.tools.common.utils.LocationUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

/**
 * Aes参数初始化配置
 * web 适用与web项目
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "common.location")
public class LocationProperties {
    private String type;
    private String serviceUrl;
    private String appKey;

    @PostConstruct
    public void initLocationConfig() {
        LocationUtil.TYPE = StringUtils.isBlank(this.type) ? AesUtil.KEY : this.type;
        LocationUtil.APP_KEY = StringUtils.isBlank(this.appKey) ? AesUtil.KEY : this.appKey;
        LocationUtil.SERVICE_URL = StringUtils.isBlank(this.serviceUrl) ? AesUtil.KEY : this.serviceUrl;
    }
}
