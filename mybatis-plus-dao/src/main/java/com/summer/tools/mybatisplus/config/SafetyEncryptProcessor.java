package com.summer.tools.mybatisplus.config;

import com.summer.tools.common.utils.AesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;

/**
 * 安全加密处理器
 */

public class SafetyEncryptProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String mpwKey = AesUtil.KEY;

        if (StringUtils.isNotBlank(mpwKey)) {
            HashMap<String, Object> map = new HashMap<>();
            for (PropertySource<?> ps : environment.getPropertySources()) {
                if (ps instanceof OriginTrackedMapPropertySource) {
                    OriginTrackedMapPropertySource source = (OriginTrackedMapPropertySource) ps;
                    for (String name : source.getPropertyNames()) {
                        Object value = source.getProperty(name);
                        if (value instanceof String) {
                            String str = (String) value;
                            if (str.startsWith("encrypt:")) {
                                map.put(name, AesUtil.decrypt(str.substring(8), mpwKey));
                            }
                        }
                    }
                }
            }
            // 将解密的数据放入环境变量，并处于第一优先级上
            if (!CollectionUtils.isEmpty(map)) {
                environment.getPropertySources().addFirst(new MapPropertySource("custom-encrypt", map));
            }
        }
    }
}
