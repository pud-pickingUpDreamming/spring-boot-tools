package com.summer.tools.mybatisplus.orm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.summer.tools.common.validation.Add;
import com.summer.tools.common.validation.Edit;
import com.summer.tools.common.validation.EnumValid;
import com.summer.tools.mybatisplus.constant.SystemModuleEnum;
import com.summer.tools.mybatisplus.orm.model.AbstractModel;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author john.wang
 * @since 2021-06-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("summer_platform_tenant_datasource")
@ApiModel(value="PlatformTenantDatasource对象", description="")
public class PlatformTenantDatasource extends AbstractModel<PlatformTenantDatasource> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer datasourceId;

    private Integer tenantId;

    private String tenantName;
    @EnumValid(clazz = SystemModuleEnum.class, message = "没有这个枚举值", groups = {Add.class, Edit.class})
    private String module;


    public static final String ID = "id";

    public static final String DATASOURCE_ID = "datasource_id";

    public static final String TENANT_ID = "tenant_id";

    public static final String TENANT_NAME = "tenant_name";

    public static final String MODULE = "module";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
