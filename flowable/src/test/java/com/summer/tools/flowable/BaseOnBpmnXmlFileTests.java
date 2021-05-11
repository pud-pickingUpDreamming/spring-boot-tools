package com.summer.tools.flowable;

import com.summer.tools.flowable.model.Expense;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@SpringBootTest
class BaseOnBpmnXmlFileTests {

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private ProcessEngine processEngine;
    @Resource
    private HttpServletResponse httpServletResponse;

    private static final Expense expense = Expense.getExpense();



    /**
     * 新建报销流程
     */
    @Test
    public String addExpense() {

        //启动流程
        HashMap<String, Object> map = new HashMap<>();
        map.put("taskUser", expense.getUserId());
        map.put("money", expense.getAmount());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Expense", map);
        log.info("提交成功.流程Id为: [{}]", processInstance.getId());
        // 提交表单任务
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }

        // 表单任务
        taskService.complete(task.getId());
        log.info("表单:{} 提交成功",task.getTaskDefinitionKey());

        return processInstance.getId();
    }

    /**
     * 查询用户所有的报销流程
     */
    @Test
    public void queryByUser() {

        // 添加报销流程
        addExpense();

        List<Task> tasks = taskService.createTaskQuery().taskAssignee(expense.getUserId()).orderByTaskCreateTime().desc().list();
        for (Task task : tasks) {
            log.info(task.toString());
        }
        log.info(Arrays.toString(tasks.toArray()));
    }

    /**
     * 通过审核
     */
    @Test
    public void agree() {
        // 添加报销流程,并获取流程id
        String processId = addExpense();

        Task task = taskService.createTaskQuery().processInstanceId(processId).active().singleResult();

        if (task == null) {
            throw new RuntimeException("流程不存在");
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
        taskService.complete(task.getId(), map);
        log.info(task.getTaskDefinitionKey());
        log.info("processed ok!");
    }

    /**
     * 拒绝
     */
    @Test
    public void reject() {
        // 添加报销流程,并获取流程id
        String processId = addExpense();

        Task task = taskService.createTaskQuery().processInstanceId(processId).active().singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "驳回");
        taskService.complete(task.getId(), map);
        log.info(task.getTaskDefinitionKey());
        log.info("reject");
    }

    /**
     * 生成流程图
     */
    @Test
    public void genProcessDiagram() throws Exception {
        // 添加报销流程,并获取流程id
        String processId = addExpense();

        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();

        //流程走完的不显示图
        if (pi == null) {
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();

        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engConf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engConf.getProcessDiagramGenerator();
        byte[] buf = new byte[1024];
        int length;
        try (InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, engConf.getActivityFontName(), engConf.getLabelFontName(), engConf.getAnnotationFontName(), engConf.getClassLoader(), 1.0, false); OutputStream out = httpServletResponse.getOutputStream()) {
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
        }
    }

}
