package com.summer.tools.mybatisplus.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author john.wang
 * @since 2021-06-23
 */
@Data
public class DatasourceContext {

    @ApiModelProperty(value = "数据源名称", required = true)
    private String sourceName;

    @ApiModelProperty(value = "数据库连接", required = true)
    private String url;

    @ApiModelProperty(value = "数据库用户名", required = true)
    private String username;

    @ApiModelProperty(value = "数据库密码", required = true)
    private String password;

    @ApiModelProperty(value = "数据库名称", required = true)
    private String dbName;
    @ApiModelProperty(value = "数据库schema")
    private String schema;
}
