package com.summer.tools.flowable.orm.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 这个类需要手动维护
 * 公共字段
 * </p>
 *
 * @author john.wang
 * @since 2021-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractModel<T extends Model<?>> extends Model<T> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private Integer creatorId;

    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updaterId;

    @ApiModelProperty(value = "数据版本号,新增时为0. 更新时把查询出来的版本号原样返回")
    @Version
    private Integer version;

    @ApiModelProperty(hidden = true)
    @TableLogic
    private Integer isDel;
}
