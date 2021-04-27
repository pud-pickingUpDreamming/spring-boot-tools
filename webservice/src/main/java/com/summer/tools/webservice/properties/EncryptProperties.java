package com.summer.tools.webservice.properties;

import com.summer.tools.common.utils.AesUtil;
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
@ConfigurationProperties(prefix = "common.encrypt")
public class EncryptProperties {
    private Boolean enable;
    private String SecretKey;
    private String encoding;

    @PostConstruct
    public void initAesConfig() {
        AesUtil.KEY = StringUtils.isBlank(this.SecretKey) ? AesUtil.KEY : this.SecretKey;
        AesUtil.ENCODING = StringUtils.isBlank(this.encoding) ? AesUtil.ENCODING : Charset.forName(this.encoding);
        AesUtil.ENABLE = enable == null ? AesUtil.ENABLE : enable;
    }
}
