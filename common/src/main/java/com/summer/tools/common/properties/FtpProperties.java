package com.summer.tools.common.properties;

import com.summer.tools.common.utils.FtpUtil;
import com.summer.tools.common.utils.SFtpUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * ftp参数初始化配置
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "common.ftp")
public class FtpProperties {
    private String host;
    private Integer port;
    private String user;
    private String password;
    private boolean secure;

    @PostConstruct
    public void initAesConfig() {
        if (this.secure) {
            SFtpUtil.HOST = StringUtils.isBlank(this.host) ? FtpUtil.HOST : this.host;
            SFtpUtil.PORT = port == null ? FtpUtil.PORT : port;
            SFtpUtil.USER = StringUtils.isBlank(this.user) ? FtpUtil.USER : user;
            SFtpUtil.PASSWORD = StringUtils.isBlank(this.password) ? FtpUtil.PASSWORD : password;
        } else {
            FtpUtil.HOST = StringUtils.isBlank(this.host) ? FtpUtil.HOST : this.host;
            FtpUtil.PORT = port == null ? FtpUtil.PORT : port;
            FtpUtil.USER = StringUtils.isBlank(this.user) ? FtpUtil.USER : user;
            FtpUtil.PASSWORD = StringUtils.isBlank(this.password) ? FtpUtil.PASSWORD : password;
        }
    }
}
