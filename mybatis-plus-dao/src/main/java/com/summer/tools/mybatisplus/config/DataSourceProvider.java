package com.summer.tools.mybatisplus.config;

import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.summer.tools.common.constants.CommonConstants;
import com.summer.tools.common.utils.JsonUtil;
import com.summer.tools.mybatisplus.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 后续可以考虑把 数据源key dataSourcePropertiesMap 放redis缓存
 * 加载数据库里面配置的数据源
 */
@Slf4j
@Component
public class DataSourceProvider extends AbstractDataSourceProvider {

	@Resource
	private DynamicDataSourceProperties properties;

	private final Map<String, DataSourceProperty> dataSourcePropertiesMap = new HashMap<>();

	private final static String QUERY_STRING = "select tds.tenant_id,tds.service,ds.* from summer_platform_db_resource ds, summer_platform_tenant_datasource tds where ds.is_del = 0 and ds.id = tds.datasource_id";

	@Override
	public Map<String, DataSource> loadDataSources() {
		MDC.put(CommonConstants.TRACE_ID, UUID.randomUUID().toString().replace("-", ""));
		log.info(JsonUtil.stringify(properties));

		DataSourceProperty dataSourceProperty = properties.getDatasource().get(properties.getPrimary());

		try(Connection conn = DriverManager.getConnection(dataSourceProperty.getUrl(), dataSourceProperty.getUsername(), dataSourceProperty.getPassword());
			PreparedStatement preparedStatement = conn.prepareStatement(QUERY_STRING);
			ResultSet rs = preparedStatement.executeQuery()) {
			while (rs.next()) {
				String key = MessageFormat.format(Constants.DS_KEY, rs.getInt("tenant_id"), rs.getString("service"));
				DataSourceProperty dsp = new DataSourceProperty();
				dsp.setUrl(rs.getString("url"))
						.setUsername(rs.getString("username"))
						.setPassword(rs.getString("password")).setSchema(rs.getString("schema"));
				dataSourcePropertiesMap.put(key, dsp);
			}
		} catch (SQLException ex) {
			log.error("默认数据库链接异常:", ex);
		}

		return this.createDataSourceMap(this.dataSourcePropertiesMap);
	}

	public Map<String, DataSourceProperty> getDataSourcePropertiesMap() {
		return this.dataSourcePropertiesMap;
	}
}
