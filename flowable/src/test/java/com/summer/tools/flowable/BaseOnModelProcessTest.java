package com.summer.tools.flowable;

import com.summer.tools.flowable.common.IdGenerator;
import com.summer.tools.flowable.constants.ProcessConstants;
import com.summer.tools.flowable.constants.TemplateConstants;
import com.summer.tools.flowable.orm.model.DeployModel;
import com.summer.tools.flowable.orm.model.ProcessLine;
import com.summer.tools.flowable.orm.model.ProcessNode;
import com.summer.tools.flowable.service.IProcessTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
public class BaseOnModelProcessTest extends AbstractControllerTest {

    @Resource
    private IProcessTemplateService flowService;

    @Test
    public void deployBaseOnModel() {
        DeployModel model = Builder.buildDeployModel();
        this.flowService.deploy(model);
    }


    static class Builder {
        static String ASSIGNEE = "john";

        static DeployModel buildDeployModel() {
            String templateId = IdGenerator.generateId(TemplateConstants.IdPrefixEnum.TEMPLATE);
            String name = "报销流程";
            DeployModel model = new DeployModel().setTemplateId(templateId).setProcessName(name);


            return model;
        }

        static ProcessNode buildNode(String templateId, ProcessConstants.ProcessNodeTypeEnum type, int index) {
            ProcessNode node = new ProcessNode()
                    .setId(IdGenerator.generateId(TemplateConstants.IdPrefixEnum.NODE))
                    .setName(type.getName() + index).setAssignee(ASSIGNEE)
                    .setTemplateId(templateId).setType(type.getValue())
                    .setHeight(10).setWidth(10).setAxisX(10*index).setAxisY(10*index);
            if (type.equals(ProcessConstants.ProcessNodeTypeEnum.USER_TASK)) {
                String listeners = ProcessConstants.ProcessListenerTypeEnum.TASK_CREATE.getType()
                        + "," + ProcessConstants.ProcessListenerTypeEnum.TASK_COMPLETE.getType();
                node.setListeners(listeners);
            }
            return node;
        }

        static ProcessLine buildLine(String templateId, ProcessNode sourceRef, ProcessNode targetRef, int index) {
            ProcessLine line = new ProcessLine()
                    .setId(IdGenerator.generateId(TemplateConstants.IdPrefixEnum.LINE))
                    .setName(TemplateConstants.IdPrefixEnum.LINE.getValue() + index)
                    .setTemplateId(templateId)
                    .setSourceRef(sourceRef.getId())
                    .setTargetRef(targetRef.getId());

            if (sourceRef.getType() == ProcessConstants.ProcessNodeTypeEnum.EXCLUSIVE_GATEWAY.getValue()) {
                sourceRef.getLineList().add(line);
            }
            return line;
        }
    }
}
