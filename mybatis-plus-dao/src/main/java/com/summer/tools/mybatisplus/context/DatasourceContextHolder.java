package com.summer.tools.mybatisplus.context;

import org.springframework.stereotype.Component;

@Component
public class DatasourceContextHolder {

    private static final ThreadLocal<TenantServiceDatasource> DATASOURCE_HOLDER = new ThreadLocal<>();

    public static TenantServiceDatasource get() {
        return DATASOURCE_HOLDER.get();
    }

    public static void remove() {
        DATASOURCE_HOLDER.remove();
    }

    public static void set(TenantServiceDatasource datasource) {
        DATASOURCE_HOLDER.set(datasource);
    }
}
