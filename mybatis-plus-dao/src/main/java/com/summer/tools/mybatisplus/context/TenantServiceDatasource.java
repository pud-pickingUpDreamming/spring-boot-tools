package com.summer.tools.mybatisplus.context;

import lombok.Data;

/**
 * 前端传租户名称,后端拿到租户名称拼接上服务名称来确定数据源
 * 微服务数据源信息
 * @author john.wang
 * @since 2021-06-23
 */
@Data
public class TenantServiceDatasource {

    /**
     * 归属租户名称
     */
    private String name;

    /**
     * 数据库资源id
     */
    private Integer dbResourceId;

    /**
     * 数据源名称(英文) 系统名称+模块名称
     */
    private String datasourceName;
}
