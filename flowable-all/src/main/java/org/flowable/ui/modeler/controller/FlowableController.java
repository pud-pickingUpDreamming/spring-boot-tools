package org.flowable.ui.modeler.controller;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/process")
public class FlowableController {

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private HistoryService historyService;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private ProcessEngine processEngine;
    @Resource
    private HttpServletResponse httpServletResponse;

    /**
     * 生成流程图,
     * @param processId 流程实例ID
     */
    @GetMapping(value = "/processDiagram")
    public void genProcessDiagram(String processId) throws Exception {
        // 查询历史流程实例
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).singleResult();
        // 查询当前流程实列
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();

        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(processId)
                .list();

        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        //获取流程图, 流程执行中高亮显示正在执行的节点,流程执行完了显示完整流程图
        String processDefinitionId = pi != null ? pi.getProcessDefinitionId() : historicProcessInstance.getProcessDefinitionId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
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
