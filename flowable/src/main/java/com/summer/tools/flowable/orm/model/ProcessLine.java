package com.summer.tools.flowable.orm.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class ProcessLine extends Model<ProcessLine> {

    /**
     *  流程线条ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
     *  流程线名称
     */
    private String name;
    /**
     *  流程模板ID
     */
    private String templateId;
    /**
     *  原节点id
     */
    private String sourceRef;
    /**
     *  目标节点id
     */
    private String targetRef;
    /**
     *  流转条件
     */
    private String conditionExpression;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
