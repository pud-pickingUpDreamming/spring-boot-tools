package org.flowable.ui.modeler;

import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.ui.modeler.constants.ProcessConstants;
import org.flowable.ui.modeler.model.FlowableElement;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@SpringBootTest
public class FlowableTest {

    @Resource
    private RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;

    private static String PROCESS_INSTANCE_ID = "c60e18e0-f5cf-11eb-bc3f-f85971bf9de7";
    private static final int MONEY = 6000;

    @Test
    public void deployExcludeGatewayFlow() {
        // 任务完成监听事件
        FlowableListener createListener = FlowElementBuilder.listener(ProcessConstants.ProcessListenerTypeEnum.TASK_CREATE);
        FlowableListener assignListener = FlowElementBuilder.listener(ProcessConstants.ProcessListenerTypeEnum.TASK_ASSIGN);
        FlowableListener completeListener = FlowElementBuilder.listener(ProcessConstants.ProcessListenerTypeEnum.TASK_COMPLETE);
        FlowableListener deleteListener = FlowElementBuilder.listener(ProcessConstants.ProcessListenerTypeEnum.TASK_DELETE);
        FlowableListener startListener = FlowElementBuilder.listener(ProcessConstants.ProcessListenerTypeEnum.SEQUENCE_TASK_START);
        FlowableListener stopListener = FlowElementBuilder.listener(ProcessConstants.ProcessListenerTypeEnum.SEQUENCE_TASK_END);
        FlowableListener takeListener = FlowElementBuilder.listener(ProcessConstants.ProcessListenerTypeEnum.SEQUENCE_TASK_TAKE);

        // 构造元素
        //开始事件
        FlowableElement start = new FlowableElement().setId("start").setName("开始");
        StartEvent startEvent = FlowElementBuilder.startEvent(start);
        //员工任务阶段
        FlowableElement employee = new FlowableElement();
        employee.setId("employee_work").setCategory("employee")
                .setAssignee("${taskUser}").setCandidateUsers(Arrays.asList("tom","tom1")).setName("申请人任务-填写表单");
        UserTask employeeTask = FlowElementBuilder.userTask(employee);
        List<FlowableListener> taskListeners = new ArrayList<>();
        taskListeners.add(createListener);
        taskListeners.add(assignListener);
        taskListeners.add(completeListener);
        taskListeners.add(deleteListener);
        employeeTask.setTaskListeners(taskListeners);

        // 审批流程决策(排他网关),金额小于5000走主管审批,金额大于5000走老板审批
        FlowableElement costGateway = new FlowableElement().setId("cost_").setName("exclusive_cost");
        ExclusiveGateway cost = FlowElementBuilder.exclusiveGateway(costGateway);

        //主管审批任务节点
        FlowableElement director = new FlowableElement();
        director.setId("direct_audit").setCategory("director")
                .setAssignee("john").setCandidateUsers(Arrays.asList("john","john1")).setName("主管审批");
        UserTask directorTask = FlowElementBuilder.userTask(director);
        // boss审批任务节点
        FlowableElement boss = new FlowableElement();
        boss.setId("boss_audit").setCategory("boss")
                .setAssignee("alis").setCandidateUsers(Arrays.asList("alis", "alis1")).setName("老板审批");
        UserTask bossTask = FlowElementBuilder.userTask(boss);

        // 通知员工(知会)
        FlowableElement employeeNotice = new FlowableElement();
        employeeNotice.setId("employee_notify").setCategory("employeeNotice")
                .setAssignee("${taskUser}").setCandidateUsers(Arrays.asList("tom","tom1")).setName("通知员工");
        UserTask employeeNoticeTask = FlowElementBuilder.userTask(employeeNotice);

        //结束事件
        FlowableElement end = new FlowableElement().setId("end").setName("结束");
        EndEvent endEvent = FlowElementBuilder.endEvent(end);

        // 构造线条
        SequenceFlow startFlow = new SequenceFlow(startEvent.getId(), employeeTask.getId());
        SequenceFlow toCostGateway = new SequenceFlow(employeeTask.getId(), costGateway.getId());
        SequenceFlow toDirectorFlow = new SequenceFlow(costGateway.getId(), directorTask.getId());
        toDirectorFlow.setConditionExpression("${money < 5000}");
        List<FlowableListener> executionListeners = new ArrayList<>();
        executionListeners.add(startListener);
        executionListeners.add(stopListener);
        executionListeners.add(takeListener);
        toDirectorFlow.setExecutionListeners(executionListeners);

        SequenceFlow toBossFlow = new SequenceFlow(costGateway.getId(), bossTask.getId());
        toBossFlow.setConditionExpression("${money >= 5000}");
        SequenceFlow directorAgreeFlow = new SequenceFlow(directorTask.getId(), employeeNoticeTask.getId());
        directorAgreeFlow.setConditionExpression("${approve}");
        SequenceFlow directorDenyFlow = new SequenceFlow(directorTask.getId(), employeeTask.getId());
        directorDenyFlow.setConditionExpression("${!approve}");
        SequenceFlow bossAgreeFlow = new SequenceFlow(bossTask.getId(), employeeNoticeTask.getId());
        bossAgreeFlow.setConditionExpression("${approve}");
        SequenceFlow bossDenyFlow = new SequenceFlow(bossTask.getId(), employeeTask.getId());
        bossDenyFlow.setConditionExpression("${!approve}");
        SequenceFlow toEndFlow = new SequenceFlow(employeeNoticeTask.getId(), endEvent.getId());

        cost.setOutgoingFlows(Arrays.asList(bossAgreeFlow, directorAgreeFlow));

        // 构造流程
        Process process = new Process();
        process.setId("ExpenseApproveExcludeListener");
        process.setName("排他网关报销审批流程-listener");
        process.addFlowElement(startEvent);
        process.addFlowElement(employeeTask);
        process.addFlowElement(cost);
        process.addFlowElement(directorTask);
        process.addFlowElement(bossTask);
        process.addFlowElement(employeeNoticeTask);
        process.addFlowElement(endEvent);
        process.addFlowElement(startFlow);
        process.addFlowElement(toCostGateway);
        process.addFlowElement(toDirectorFlow);
        process.addFlowElement(toBossFlow);
        process.addFlowElement(directorAgreeFlow);
        process.addFlowElement(directorDenyFlow);
        process.addFlowElement(bossAgreeFlow);
        process.addFlowElement(bossDenyFlow);
        process.addFlowElement(toEndFlow);
        BpmnModel bpmnModel = new BpmnModel();
        bpmnModel.addProcess(process);
        // 生成流程图
        new BpmnAutoLayout(bpmnModel).execute();

        // 验证模型数据格式
        ProcessValidatorFactory processValidatorFactory = new ProcessValidatorFactory();
        ProcessValidator defaultProcessValidator = processValidatorFactory.createDefaultProcessValidator();
        List<ValidationError> validate = defaultProcessValidator.validate(bpmnModel);
        if (!CollectionUtils.isEmpty(validate)) {
            System.out.println("流程模型有问题:" + validate.get(0).getDefaultDescription());
        }

        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        byte[] convertToXML = bpmnXMLConverter.convertToXML(bpmnModel);
        String xmlData = new String(convertToXML);
        System.out.println(xmlData);
        Deployment deploy = repositoryService.createDeployment().addString("排他网关报销审批流程-listener.bpmn", xmlData).name("排他网关报销审批流程(监听器)").deploy();
        System.out.println("部署id :" + deploy.getId());
    }

    @Test
    public void deployParallelGatewayFlow() {
        // 任务完成监听事件
        FlowableListener completeListener = FlowElementBuilder.listener(ProcessConstants.ProcessListenerTypeEnum.TASK_COMPLETE);
        FlowableListener createListener = FlowElementBuilder.listener(ProcessConstants.ProcessListenerTypeEnum.TASK_CREATE);

        // 构造元素
        //开始事件
        FlowableElement start = new FlowableElement().setId("start").setName("开始");
        StartEvent startEvent = FlowElementBuilder.startEvent(start);
        //员工任务阶段
        FlowableElement employee = new FlowableElement();
        employee.setId("employee_work").setCategory("employee")
                .setAssignee("${taskUser}").setCandidateUsers(Arrays.asList("tom","tom1")).setName("申请人任务-填写表单");
        UserTask employeeTask = FlowElementBuilder.userTask(employee);
        employeeTask.setTaskListeners(Collections.singletonList(completeListener));

        // 并行知会人事
        FlowableElement forkGateway = new FlowableElement().setId("fork_").setName("parallel_fork");
        ParallelGateway fork = FlowElementBuilder.parallelGateway(forkGateway);

        //主管审批任务节点
        FlowableElement director = new FlowableElement();
        director.setId("direct_audit").setCategory("director")
                .setAssignee("john").setCandidateUsers(Arrays.asList("john","john1")).setName("主管审批");
        UserTask directorTask = FlowElementBuilder.userTask(director);

        // hr审批任务节点(知会)
        FlowableElement hr = new FlowableElement();
        hr.setId("hr_notify").setCategory("hr")
                .setAssignee("jack").setCandidateUsers(Arrays.asList("jack", "jack1")).setName("知会人事");
        UserTask hrTask = FlowElementBuilder.userTask(hr);
        hrTask.setTaskListeners(Collections.singletonList(createListener));

        // 通知员工(知会)
        FlowableElement employeeNotice = new FlowableElement();
        employeeNotice.setId("employee_notify").setCategory("employeeNotice")
                .setAssignee("${taskUser}").setCandidateUsers(Arrays.asList("tom","tom1")).setName("通知员工");
        UserTask employeeNoticeTask = FlowElementBuilder.userTask(employeeNotice);

        FlowableElement joinGateway = new FlowableElement().setId("join_").setName("parallel_join");
        ParallelGateway join = FlowElementBuilder.parallelGateway(joinGateway);

        //结束事件
        FlowableElement end = new FlowableElement().setId("end").setName("结束");
        EndEvent endEvent = FlowElementBuilder.endEvent(end);

        // 构造线条
        SequenceFlow startFlow = new SequenceFlow(startEvent.getId(), employeeTask.getId());
        SequenceFlow toForkFlow = new SequenceFlow(employeeTask.getId(), fork.getId());
        // 知会人事不需要条件
        SequenceFlow toHrFlow = new SequenceFlow(fork.getId(), hrTask.getId());
        SequenceFlow toDirectorFlow = new SequenceFlow(fork.getId(), directorTask.getId());
        SequenceFlow directorAgreeFlow = new SequenceFlow(directorTask.getId(), join.getId());
        directorAgreeFlow.setConditionExpression("${approve}");
        SequenceFlow directorDenyFlow = new SequenceFlow(directorTask.getId(), employeeTask.getId());
        directorDenyFlow.setConditionExpression("${!approve}");
        SequenceFlow toJoinFlow = new SequenceFlow(hrTask.getId(), join.getId());
        SequenceFlow toEmployeeNoticeFlow = new SequenceFlow(join.getId(), employeeNoticeTask.getId());
        SequenceFlow toEndFlow = new SequenceFlow(employeeNoticeTask.getId(), endEvent.getId());

        // 构造流程
        Process process = new Process();
        process.setId("ExpenseApproveParallel");
        process.setName("并行网关报销审批流程");
        process.addFlowElement(startEvent);
        process.addFlowElement(employeeTask);
        process.addFlowElement(fork);
        process.addFlowElement(directorTask);
        process.addFlowElement(hrTask);
        process.addFlowElement(join);
        process.addFlowElement(employeeNoticeTask);
        process.addFlowElement(endEvent);
        process.addFlowElement(startFlow);
        process.addFlowElement(toForkFlow);
        process.addFlowElement(toHrFlow);
        process.addFlowElement(toDirectorFlow);
        process.addFlowElement(directorAgreeFlow);
        process.addFlowElement(directorDenyFlow);
        process.addFlowElement(toJoinFlow);
        process.addFlowElement(toEmployeeNoticeFlow);
        process.addFlowElement(toEndFlow);
        BpmnModel bpmnModel = new BpmnModel();
        bpmnModel.addProcess(process);
        // 生成流程图
        new BpmnAutoLayout(bpmnModel).execute();

        // 验证模型数据格式
        ProcessValidatorFactory processValidatorFactory = new ProcessValidatorFactory();
        ProcessValidator defaultProcessValidator = processValidatorFactory.createDefaultProcessValidator();
        List<ValidationError> validate = defaultProcessValidator.validate(bpmnModel);
        if (!CollectionUtils.isEmpty(validate)) {
            System.out.println("流程模型有问题:" + validate.get(0).getDefaultDescription());
        }

        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        byte[] convertToXML = bpmnXMLConverter.convertToXML(bpmnModel);
        String xmlData = new String(convertToXML);
        System.out.println(xmlData);
        Deployment deploy = repositoryService.createDeployment().addString("并行网关报销审批流程.bpmn", xmlData).name("并行网关报销审批流程").deploy();
        System.out.println("部署id :" + deploy.getId());
    }

    /**
     * 新建报销流程
     */
    @Test
    public void startProcess() {

        //启动流程
        Map<String, Object> params = new HashMap<>();
        params.put("taskUser", "tom");
        params.put("money", MONEY);
        // ExpenseApproveExclude ExpenseApproveParallel ExpenseApproveExcludeListener
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("ExpenseApproveExcludeListener", params);
        log.info("提交成功.流程Id为: [{}]", processInstance.getId());
        // 提交表单任务
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }

        // 表单任务
        taskService.complete(task.getId(), params);
        log.info("表单:{} 提交成功", task.getTaskDefinitionKey());
    }

    /**
     * 通过审核
     */
    @Test
    public void agree() {
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(PROCESS_INSTANCE_ID).active().list();

        if (CollectionUtils.isEmpty(tasks)) {
            throw new RuntimeException("流程不存在");
        }
        tasks.forEach(f -> {
            // 如果是知会直接完成,让流程流转下去
            if ("hr".equals(f.getCategory()) || "employeeNotice".equals(f.getCategory())) {
                taskService.complete(f.getId());
            } else {
                HashMap<String, Object> map = new HashMap<>();
                map.put("approve", true);
                map.put("outcome", "通过");
                taskService.complete(f.getId(), map);
                log.info(f.getTaskDefinitionKey());
                log.info("processed ok!");
            }
        });
    }

    /**
     * 员工确认
     */
    @Test
    public void confirm() {
        Task task = taskService.createTaskQuery().processInstanceId(PROCESS_INSTANCE_ID).active().singleResult();

        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        taskService.complete(task.getId());
    }

    static class FlowElementBuilder {
        static StartEvent startEvent(FlowableElement element) {
            StartEvent startEvent = new StartEvent();
            startEvent.setId(element.getId());
            startEvent.setName(element.getName());
            return startEvent;
        }

        static EndEvent endEvent(FlowableElement element) {
            EndEvent endEvent = new EndEvent();
            endEvent.setId(element.getId());
            endEvent.setName(element.getName());
            // 结束终止流程
            TerminateEventDefinition definition = new TerminateEventDefinition();
            definition.setTerminateAll(true);
            endEvent.addEventDefinition(definition);
            return endEvent;
        }

        static UserTask userTask(FlowableElement element) {
            UserTask userTask = new UserTask();
            userTask.setId(element.getId());
            userTask.setName(element.getName());
            userTask.setCategory(element.getCategory());
            userTask.setAssignee(element.getAssignee());
            userTask.setCandidateGroups(element.getCandidateUsers());
            return userTask;
        }

        @SuppressWarnings("unused")
        static FlowElement serviceTask(FlowableElement element) {
            ServiceTask serviceTask = new ServiceTask();
            serviceTask.setId(element.getId());
            serviceTask.setName(element.getName());
            return serviceTask;
        }

        static ExclusiveGateway exclusiveGateway(FlowableElement element) {
            ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
            exclusiveGateway.setId(element.getId());
            exclusiveGateway.setName(element.getName());
            return exclusiveGateway;
        }

        static ParallelGateway parallelGateway(FlowableElement element) {
            ParallelGateway parallelGateway = new ParallelGateway();
            parallelGateway.setId(element.getId());
            parallelGateway.setName(element.getName());
            return parallelGateway;
        }

        @SuppressWarnings("unused")
        static SequenceFlow sequenceFlow(FlowableElement element) {
            SequenceFlow sequenceFlow = new SequenceFlow();
            sequenceFlow.setId(element.getId());
            sequenceFlow.setName(element.getName());
            sequenceFlow.setSourceRef(element.getSourceRef().getId());
            sequenceFlow.setTargetRef(element.getTargetRef().getId());
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
