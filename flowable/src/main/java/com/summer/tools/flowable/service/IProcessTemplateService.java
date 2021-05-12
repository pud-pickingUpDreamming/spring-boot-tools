package com.summer.tools.flowable.service;

import com.summer.tools.flowable.orm.model.DeployModel;
import org.flowable.engine.repository.ProcessDefinition;

public interface IProcessTemplateService {

    ProcessDefinition deploy(String processId, String flowName, String resource);

    ProcessDefinition deploy(DeployModel deployModel);
}
