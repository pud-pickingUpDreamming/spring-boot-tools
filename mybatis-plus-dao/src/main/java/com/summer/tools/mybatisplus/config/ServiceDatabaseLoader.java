package com.summer.tools.mybatisplus.config;

import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import com.baomidou.dynamic.datasource.provider.YmlDynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Map;

public class ServiceDatabaseLoader extends AbstractDataSourceProvider {

    private static final Logger log = LoggerFactory.getLogger(YmlDynamicDataSourceProvider.class);
    private final Map<String, DataSourceProperty> dataSourcePropertiesMap;

    @Override
    public Map<String, DataSource> loadDataSources() {
        return this.createDataSourceMap(this.dataSourcePropertiesMap);
    }

    public ServiceDatabaseLoader(Map<String, DataSourceProperty> dataSourcePropertiesMap) {
        this.dataSourcePropertiesMap = dataSourcePropertiesMap;
    }
}
