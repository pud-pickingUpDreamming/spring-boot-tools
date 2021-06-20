package com.summer.tools.mybatisplus.orm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 平台租户信息表
 * </p>
 *
 * @author john.wang
 * @since 2021-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("summer_platform_tenant")
public class PlatformTenant extends Model<PlatformTenant> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 租户名称
     */
    private String name;

    /**
     * 数据库资源id
     */
    private Integer dbResourceId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 企业电话
     */
    private String companyTel;

    /**
     * 企业邮箱
     */
    private String companyEmail;

    /**
     * 企业地址
     */
    private String companyAddress;

    /**
     * 状态
     */
    private String status;

    /**
     * 图标
     */
    private String logo;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
