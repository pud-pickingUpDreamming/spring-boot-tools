package com.summer.tools.flowable.service;

import com.summer.tools.flowable.orm.model.DeployModel;
import org.flowable.engine.repository.ProcessDefinition;

public interface IProcessTemplateService {

    /**
     * 根据xml文件部署流程
     * @param flowName 流程名称
     * @param resource 流程资源
     * @return 流程定义对象
     */
    ProcessDefinition deploy(String flowName, String resource);

    ProcessDefinition deploy(DeployModel deployModel);
}
