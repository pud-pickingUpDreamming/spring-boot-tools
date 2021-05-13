package com.summer.tools.flowable.orm.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.summer.tools.flowable.constants.ProcessConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ProcessNode extends Model<ProcessNode> {

    /**
     *  流程节点ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
     *  流程节点名称
     */
    private String name;
    /**
     *  流程模板ID
     */
    private String templateId;
    /**
     * @see ProcessConstants.ProcessNodeTypeEnum
     * 节点类型
     */
    private Integer type;
    /**
     *  服务实现类型 class
     */
    private String implementationType;
    /**
     *  服务节点选择class 类型时，bean名称
     */
    private String implementation;
    /**
     *  处理人
     */
    private String assignee;

    /**
     * 全限定类名,多个用","分割
     * 任务节点监听器
     */
    private String listeners;

    /**
     * 线条id,关联线条,多条用","分割
     * 网关会对应多个线条
     */
    private String lines;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    @TableField(exist = false)
    private ProcessConstants.ProcessNodeTypeEnum typeEnum;
    @TableField(exist = false)
    private List<ProcessConstants.ProcessListenerTypeEnum> listenerList;
    @TableField(exist = false)
    private List<ProcessLine> lineList;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
