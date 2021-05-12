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
public class ProcessLine {

    /**
     *  流程线条ID
     */
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
}
