package com.summer.tools.flowable.common;

import com.summer.tools.common.utils.Assert;
import com.summer.tools.common.utils.BeanUtil;
import com.summer.tools.flowable.constants.ProcessConstants;
import com.summer.tools.flowable.orm.model.DeployModel;
import com.summer.tools.flowable.orm.model.ProcessLine;
import com.summer.tools.flowable.orm.model.ProcessNode;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AbstractProcessManager {
    protected static final String PROCESS_PREFIX = "process-";
    protected static final String PROCESS_POSTFIX = "-流程";

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private ProcessEngine processEngine;

    /**
     * 基于xml流程文件部署流程,部署简单,因为很多东西都是在xml文件里定义的,不够灵活
     * @param resource 流程定义xml文件(classpath resource)
     * @return 描述流程信息的流程定义对象。
     */
    protected ProcessDefinition deploy(String templateId, String processName, String resource) {
        log.info("流程[{}]开始构建! 参数:{}", processName, resource);
        // 1. 部署流程
        Deployment deployment = this.repositoryService.createDeployment()
                .name(processName + PROCESS_POSTFIX)
                .addClasspathResource(resource)
                .deploy();
        // 2. 获取流程定义对象
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();
        log.info("processDefinitionId:{}, processDefinitionKey:{}", processDefinition.getId(), processDefinition.getKey());
        log.info("流程[{}]部署成功! 流程定义唯一标识:{}", processName, processDefinition.getKey());
        // 3. 发布回调
        this.postDeploy(templateId, processDefinition.getKey(), resource);

        return processDefinition;
    }

    protected ProcessDefinition deploy(DeployModel deployModel) {
        log.info("流程[{}]开始构建! 参数:{}", deployModel.getProcessName(), deployModel.getTemplateId());
        // 1. 构造流程模型
        BpmnModel bpmnModel = this.BpmnModelWrapper(deployModel);

        // 2. 校验模型
        ProcessValidator processValidator = new ProcessValidatorFactory().createDefaultProcessValidator();
        List<ValidationError> validationErrorList = processValidator.validate(bpmnModel);
        Assert.empty(validationErrorList);

        // 3. 部署流程
        Deployment deployment = this.repositoryService.createDeployment()
                .name(deployModel.getProcessName() + PROCESS_POSTFIX)
                .addBpmnModel(deployModel.getProcessName(), bpmnModel)
                .deploy();
        // 4. 获取流程定义对象
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();
        log.info("流程[{}]部署成功! 流程定义唯一标识:{}", deployModel.getProcessName(), processDefinition.getKey());
        // 5. 发布回调
        this.postDeploy(deployModel.getTemplateId(), processDefinition.getKey(), bpmnModel);

        return processDefinition;
    }

    private BpmnModel BpmnModelWrapper(DeployModel deployModel) {
        // 设置流程基本属性
        Process process = new Process();
        process.setDocumentation(deployModel.getProcessName() + "流程");
        process.setId(PROCESS_PREFIX + deployModel.getTemplateId());
        process.setName(deployModel.getProcessName());

        // 获取节点列表
        List<FlowElement> nodeElements = processNodesWrapper(deployModel.getProcessNodes());
        // 获取线段列表
        List<FlowElement> lineElements = processLineWrapper(deployModel.getProcessLines());

        // 把线条和节点添加到流程对象里
        nodeElements.forEach(process::addFlowElement);
        lineElements.forEach(process::addFlowElement);

        BpmnModel bpmnModel = new BpmnModel();
        bpmnModel.addProcess(process);

        return bpmnModel;
    }

    private List<FlowElement> processNodesWrapper(List<ProcessNode> processNodes) {
        List<FlowElement> elements = new ArrayList<>();
        if (CollectionUtils.isEmpty(processNodes)) {
            return elements;
        }
        processNodes.forEach(processNode -> {
            switch (processNode.getTypeEnum()) {
                case START_NODE:
                    elements.add(FlowElementBuilder.startEvent(processNode));
                    break;
                case END_NODE:
                    elements.add(FlowElementBuilder.endEvent(processNode));
                    break;
                case USER_TASK:
                    elements.add(FlowElementBuilder.userTask(processNode));
                    break;
                case SERVICE_TASK:
                    elements.add(FlowElementBuilder.serviceTask(processNode));
                    break;
                case EXCLUSIVE_GATEWAY:
                    elements.add(FlowElementBuilder.exclusiveGateway(processNode));
                    break;
                case PARALLEL_GATEWAY:
                    elements.add(FlowElementBuilder.parallelGateway(processNode));
                    break;
                default:
                    break;
            }
        });
        return elements;
    }

    private List<FlowElement> processLineWrapper(List<ProcessLine> processLines) {
        List<FlowElement> elements = new ArrayList<>();
        if (CollectionUtils.isEmpty(processLines)) {
            return elements;
        }
        processLines.forEach(processLine -> elements.add(FlowElementBuilder.sequenceFlow(processLine)));
        return elements;
    }

    /**
     * 发布回调函数
     */
    protected <T> void postDeploy(String templateId, String processDefinitionId, T t) {
        log.info("流程模板id: [{}], 流程定义唯一标识: [{}]", templateId, processDefinitionId);
    }

    static class FlowElementBuilder {
        static FlowElement startEvent(ProcessNode processNode) {
            StartEvent startEvent = new StartEvent();
            BeanUtil.copyProperties(processNode, startEvent, null);
            startEvent.setName(processNode.getName() + "_开始");
            return startEvent;
        }

        static FlowElement endEvent(ProcessNode processNode) {
            EndEvent endEvent = new EndEvent();
            BeanUtil.copyProperties(processNode, endEvent, null);
            endEvent.setName(processNode.getName() + "_结束");
            // 结束终止流程
            TerminateEventDefinition definition = new TerminateEventDefinition();
            definition.setTerminateAll(true);
            endEvent.addEventDefinition(definition);
            return endEvent;
        }

        static FlowElement userTask(ProcessNode processNode) {
            UserTask userTask = new UserTask();
            BeanUtil.copyProperties(processNode, userTask, null);
            if (processNode.getListenerList() != null) {
                List<FlowableListener> listenerList = new ArrayList<>();
                processNode.getListenerList().forEach(listener -> listenerList.add(FlowElementBuilder.listener(listener)));
                userTask.setTaskListeners(listenerList);
            }
            return userTask;
        }

        static FlowElement serviceTask(ProcessNode processNode) {
            ServiceTask serviceTask = new ServiceTask();
            BeanUtil.copyProperties(processNode, serviceTask, null);
            return serviceTask;
        }

        static FlowElement exclusiveGateway(ProcessNode processNode) {
            ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
            BeanUtil.copyProperties(processNode, exclusiveGateway, null);

            if (processNode.getLineList() != null) {
                List<SequenceFlow> lineList = new ArrayList<>();
                processNode.getLineList().forEach(processLine -> lineList.add(FlowElementBuilder.sequenceFlow(processLine)));
                exclusiveGateway.setOutgoingFlows(lineList);
            }
            return exclusiveGateway;
        }

        static FlowElement parallelGateway(ProcessNode processNode) {
            ParallelGateway parallelGateway = new ParallelGateway();
            BeanUtil.copyProperties(processNode, parallelGateway, null);
            return parallelGateway;
        }

        static SequenceFlow sequenceFlow(ProcessLine processLine) {
            SequenceFlow sequenceFlow = new SequenceFlow();
            BeanUtil.copyProperties(processLine, sequenceFlow, null);
            return sequenceFlow;
        }

        static FlowableListener listener(ProcessConstants.ProcessListenerTypeEnum listenerType) {
            FlowableListener listener = new FlowableListener();
            listener.setEvent(listenerType.getType());
            listener.setImplementationType(listenerType.getImplementationType());
            listener.setImplementation(listenerType.getListener().getClass().getName());
            return listener;
        }
    }


}
