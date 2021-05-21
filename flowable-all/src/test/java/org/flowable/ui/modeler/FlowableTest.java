package org.flowable.ui.modeler;

import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.ui.modeler.listeners.PermissionTaskListener;
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
    @Resource
    private ProcessEngine processEngine;
    private static String PROCESS_INSTANCE_ID = "";
    private static final int MONEY = 6000;

    @Test
    public void deploy() {
        // 构造元素
        //开始事件
        StartEvent startEvent = new StartEvent();
        startEvent.setId("start");
        startEvent.setName("开始");
        //员工任务阶段
        UserTask employeeTask = new UserTask();
        employeeTask.setId("employee_work");
        employeeTask.setCategory("employee");
        employeeTask.setAssignee("${taskUser}");
        employeeTask.setCandidateUsers(Arrays.asList("tom","tom1"));
        employeeTask.setName("申请人任务-填写表单");
        // 员工任务监听事件
        List<FlowableListener> taskListeners = new ArrayList<>();
        FlowableListener listener = new FlowableListener();
        listener.setEvent("complete");
        listener.setImplementationType("class");
        listener.setImplementation(PermissionTaskListener.class.getName());
        taskListeners.add(listener);
        employeeTask.setTaskListeners(taskListeners);
        // 并行知会人事
        ParallelGateway fork = new ParallelGateway();
        fork.setId("fork_");
        fork.setName("parallel_fork");
        // 审批流程决策(排他网关),金额小于5000走主管审批,金额大于5000走老板审批
        ExclusiveGateway costGateway = new ExclusiveGateway();
        costGateway.setId("cost_");
        //主管审批任务节点
        UserTask directorTask = new UserTask();
        directorTask.setId("direct_audit");
        directorTask.setCategory("director");
        directorTask.setCandidateUsers(Arrays.asList("john","john1"));
        directorTask.setAssignee("john");
        directorTask.setName("主管审批");

        UserTask bossTask = new UserTask();
        bossTask.setId("boss_audit");
        bossTask.setCategory("boss");
        bossTask.setCandidateUsers(Arrays.asList("alis", "alis1"));
        bossTask.setAssignee("alis");
        bossTask.setName("老板审批");

        ExclusiveGateway endGateway = new ExclusiveGateway();
        endGateway.setId("end_gateway_");

        UserTask hrTask = new UserTask();
        hrTask.setId("hr_notify");
        hrTask.setCategory("hr");
        hrTask.setCandidateUsers(Arrays.asList("jack", "jack1"));
        hrTask.setAssignee("jack");
        hrTask.setName("知会人事");

        ParallelGateway join = new ParallelGateway();
        join.setId("join_");
        join.setName("parallel_join");

        //结束事件
        EndEvent endEvent = new EndEvent();
        endEvent.setId("end");
        endEvent.setName("结束");

        // 构造线条
        SequenceFlow startFlow = new SequenceFlow(startEvent.getId(), employeeTask.getId());
        SequenceFlow toForkFlow = new SequenceFlow(employeeTask.getId(), fork.getId());
        // 知会人事不需要条件
        SequenceFlow toHrFlow = new SequenceFlow(fork.getId(), hrTask.getId());
        SequenceFlow toCostGateway = new SequenceFlow(fork.getId(), costGateway.getId());
        SequenceFlow toDirectorFlow = new SequenceFlow(costGateway.getId(), directorTask.getId());
        toDirectorFlow.setConditionExpression("${money < 5000}");
        SequenceFlow toBossFlow = new SequenceFlow(costGateway.getId(), bossTask.getId());
        toBossFlow.setConditionExpression("${money >= 5000}");
        SequenceFlow directorAgreeFlow = new SequenceFlow(directorTask.getId(), endGateway.getId());
        directorAgreeFlow.setConditionExpression("${approve}");
        SequenceFlow directorDenyFlow = new SequenceFlow(directorTask.getId(), employeeTask.getId());
        directorDenyFlow.setConditionExpression("${!approve}");
        SequenceFlow bossAgreeFlow = new SequenceFlow(bossTask.getId(), endGateway.getId());
        bossAgreeFlow.setConditionExpression("${approve}");
        SequenceFlow bossDenyFlow = new SequenceFlow(bossTask.getId(), employeeTask.getId());
        bossDenyFlow.setConditionExpression("${!approve}");
        SequenceFlow endGatewayToJoinFlow = new SequenceFlow(endGateway.getId(), join.getId());
        SequenceFlow toJoinFlow = new SequenceFlow(hrTask.getId(), join.getId());
        SequenceFlow toEndFlow = new SequenceFlow(join.getId(), endEvent.getId());

        // 构造流程
        Process process = new Process();
        process.setId("ExpenseApprove");
        process.setName("报销审批流程");
        process.addFlowElement(startEvent);
        process.addFlowElement(employeeTask);
        process.addFlowElement(fork);
        process.addFlowElement(costGateway);
        process.addFlowElement(directorTask);
        process.addFlowElement(bossTask);
        process.addFlowElement(endGateway);
        process.addFlowElement(hrTask);
        process.addFlowElement(join);
        process.addFlowElement(endEvent);
        process.addFlowElement(startFlow);
        process.addFlowElement(toForkFlow);
        process.addFlowElement(toHrFlow);
        process.addFlowElement(toCostGateway);
        process.addFlowElement(toDirectorFlow);
        process.addFlowElement(toBossFlow);
        process.addFlowElement(directorAgreeFlow);
        process.addFlowElement(directorDenyFlow);
        process.addFlowElement(bossAgreeFlow);
        process.addFlowElement(bossDenyFlow);
        process.addFlowElement(endGatewayToJoinFlow);
        process.addFlowElement(toJoinFlow);
        process.addFlowElement(toEndFlow);
        BpmnModel bpmnModel = new BpmnModel();
        bpmnModel.addProcess(process);
        // 生成流程图
//        new BpmnAutoLayout(bpmnModel).execute();

        // 验证模型数据格式
        ProcessValidatorFactory processValidatorFactory = new ProcessValidatorFactory();
        ProcessValidator defaultProcessValidator = processValidatorFactory.createDefaultProcessValidator();
        List<ValidationError> validate = defaultProcessValidator.validate(bpmnModel);
        if (!CollectionUtils.isEmpty(validate)) {
            System.out.println("流程模型有问题");
        }

        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        byte[] convertToXML = bpmnXMLConverter.convertToXML(bpmnModel);
        String xmlData = new String(convertToXML);
        System.out.println(xmlData);
        Deployment deploy = repositoryService.createDeployment().addString("报销审批流程.bpmn", xmlData).deploy();
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
//        params.put("permissionTaskListener", PermissionTaskListener.class.getName());

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("ExpenseApprove", params);
        log.info("提交成功.流程Id为: [{}]", processInstance.getId());
        // 提交表单任务
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }

        // 表单任务
        taskService.complete(task.getId(), params);
        log.info("表单:{} 提交成功", task.getTaskDefinitionKey());

        PROCESS_INSTANCE_ID = processInstance.getId();
    }

    /**
     * 通过审核
     */
    @Test
    public void agree() {
        // 添加报销流程,并获取流程id
        startProcess();
        String currentUser = MONEY > 5000 ? "alis" : "john";
        Task task = taskService.createTaskQuery().processInstanceId(PROCESS_INSTANCE_ID).taskCandidateOrAssigned(currentUser).active().singleResult();

        if (task == null) {
            throw new RuntimeException("流程不存在");
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("approve", true);
        taskService.complete(task.getId(), map);
        log.info(task.getTaskDefinitionKey());
        log.info("processed ok!");
    }
}
