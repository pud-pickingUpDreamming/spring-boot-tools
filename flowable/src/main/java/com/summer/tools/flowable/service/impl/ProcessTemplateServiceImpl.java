package com.summer.tools.flowable.service.impl;

import com.summer.tools.flowable.common.AbstractProcessManager;
import com.summer.tools.flowable.orm.model.DeployModel;
import com.summer.tools.flowable.service.IProcessTemplateService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

@Service
public class ProcessTemplateServiceImpl extends AbstractProcessManager implements IProcessTemplateService {

    @Override
    public ProcessDefinition deploy(String processId, String processName, String resource) {
        return super.deploy(processId, processName, resource);
    }

    @Override
    public ProcessDefinition deploy(DeployModel deployModel) {


        return super.deploy(deployModel);
    }
}
