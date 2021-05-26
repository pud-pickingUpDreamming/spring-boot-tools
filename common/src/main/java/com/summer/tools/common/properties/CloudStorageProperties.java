package com.summer.tools.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 云存储属性映射文件
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "common.cloud-storage")
public class CloudStorageProperties {
    /**
     * 账号
     */
    private String accountName;
    /**
     * 密码
     */
    private String accountKey;
    /**
     * 端点
     */
    private String endPoint;
    /**
     * 协议
     */
    private String protocol;
    /**
     * 容器
     */
    private String containerName;
    /**
     * 基础路径
     */
    private String baseUrl;
    /**
     * 类型  azure:微软云
     */
    private String type;
}
