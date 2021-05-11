package com.summer.tools.flowable.orm.model;

import com.summer.tools.flowable.constants.ProcessConstants;
import lombok.Data;

import java.util.List;

@Data
public class ProcessNode {

    ProcessConstants.ProcessNodeTypeEnum nodeType;
    /**
     *  流程节点ID
     */
    String nodeId;
    /**
     *  流程节点名称
     */
    String nodeName;
    /**
     *  服务节点类型
     *
     */
    String serviceType;
    /**
     *  服务节点再选择class 类型时，bean名称
     */
    String serviceBeanName;
    /**
     *  认领任务用户
     */
    String assignee;

    List<ProcessListener> listeners;


    List<ProcessLine> gatewayLines;
}
