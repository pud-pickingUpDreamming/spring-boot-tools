package org.flowable.ui.modeler.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.ui.modeler.constants.ProcessConstants;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class FlowableElement {
    private String id;
    private String name;
    // 节点相关
    private ProcessConstants.ProcessNodeTypeEnum type;
    private String category;
    private String assignee;
    private List<String> candidateUsers;
    // 排他网关需要设置outgoingFlows否则验证有问题
    private List<SequenceFlow> sequenceFlows;
    // sequence相关
    private String conditionExpression;
    private FlowNode sourceRef;
    private FlowNode targetRef;
}
