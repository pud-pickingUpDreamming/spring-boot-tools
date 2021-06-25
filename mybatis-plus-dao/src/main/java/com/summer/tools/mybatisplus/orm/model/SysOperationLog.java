package com.summer.tools.mybatisplus.orm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.summer.tools.mybatisplus.orm.model.AbstractModel;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统操作日志表
 * </p>
 *
 * @author john.wang
 * @since 2021-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("summer_sys_operation_log")
@ApiModel(value="SysOperationLog对象", description="系统操作日志表")
public class SysOperationLog extends AbstractModel<SysOperationLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "操作模块")
    private String module;

    @ApiModelProperty(value = "功能")
    private String function;

    @ApiModelProperty(value = "操作接口")
    private String url;

    @ApiModelProperty(value = "操作内容")
    private String params;

    @ApiModelProperty(value = "操作结果")
    private String result;

    @ApiModelProperty(value = "操作耗时")
    private String cost;

    @ApiModelProperty(value = "操作人ip")
    private String ipAddr;

    @ApiModelProperty(value = "操作人位置")
    private String location;

    @ApiModelProperty(value = "操作人名称", hidden = true)
    private String creator;


    public static final String ID = "id";

    public static final String MODULE = "module";

    public static final String FUNCTION = "function";

    public static final String URL = "url";

    public static final String PARAMS = "params";

    public static final String RESULT = "result";

    public static final String COST = "cost";

    public static final String IP_ADDR = "ip_addr";

    public static final String LOCATION = "location";

    public static final String CREATOR = "creator";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
