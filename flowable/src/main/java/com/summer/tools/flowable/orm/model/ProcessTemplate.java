package com.summer.tools.flowable.orm.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class ProcessTemplate extends Model<ProcessTemplate> {

    /**
     *  流程模板ID
     */
    @TableId(value = "template_id", type = IdType.INPUT)
    private String templateId;
    /**
     *  流程模板名称
     */
    private String name;
    /**
     *  流程模板类型 1:审批流程
     */
    private Integer type;
    /**
     *  流程描述
     */
    private String description;
    /**
     *  流程xml内容
     */
    private String xmlData;
    /**
     *  流程模板状态 1: 草稿 2: 发布 3: 删除
     */
    private Integer status;

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
        return this.templateId;
    }
}
