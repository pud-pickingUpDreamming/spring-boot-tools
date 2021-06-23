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
 * 平台租户信息表
 * </p>
 *
 * @author john.wang
 * @since 2021-06-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("summer_platform_tenant")
@ApiModel(value="PlatformTenant对象", description="平台租户信息表")
public class PlatformTenant extends AbstractModel<PlatformTenant> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "租户名称")
    private String name;

    @ApiModelProperty(value = "数据库资源id")
    private Integer dbResourceId;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "企业名称")
    private String companyName;

    @ApiModelProperty(value = "企业电话")
    private String companyTel;

    @ApiModelProperty(value = "企业邮箱")
    private String companyEmail;

    @ApiModelProperty(value = "企业地址")
    private String companyAddress;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "图标")
    private String logo;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String DB_RESOURCE_ID = "db_resource_id";

    public static final String BRAND_NAME = "brand_name";

    public static final String COMPANY_NAME = "company_name";

    public static final String COMPANY_TEL = "company_tel";

    public static final String COMPANY_EMAIL = "company_email";

    public static final String COMPANY_ADDRESS = "company_address";

    public static final String STATUS = "status";

    public static final String LOGO = "logo";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
