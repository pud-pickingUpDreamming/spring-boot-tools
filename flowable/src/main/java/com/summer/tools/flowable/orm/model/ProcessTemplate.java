package com.summer.tools.flowable.orm.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class ProcessTemplate {

    /**
     *  流程模板ID
     */
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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;
}
