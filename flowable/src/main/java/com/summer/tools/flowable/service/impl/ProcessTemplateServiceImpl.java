package com.summer.tools.flowable.service.impl;

import com.summer.tools.flowable.common.AbstractProcessManager;
import com.summer.tools.flowable.constants.ProcessConstants;
import com.summer.tools.flowable.orm.model.DeployModel;
import com.summer.tools.flowable.orm.model.ProcessTemplate;
import com.summer.tools.flowable.service.IProcessTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.util.ReflectUtil;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
public class ProcessTemplateServiceImpl extends AbstractProcessManager implements IProcessTemplateService {

    @Override
    public ProcessDefinition deploy(String processName, String resource) {
        String templateId = UUID.randomUUID().toString();
        new ProcessTemplate().setTemplateId(templateId).setType(ProcessConstants.ProcessTypeEnum.APPROVE.getValue())
                .setName(processName).setDescription(processName + PROCESS_POSTFIX)
                .insert();
        return super.deploy(templateId, processName, resource);
    }

    @Override
    public ProcessDefinition deploy(DeployModel deployModel) {


        return super.deploy(deployModel);
    }

    @Override
    protected <T> void postDeploy(String templateId, String processDefinitionId, T t) {

        super.postDeploy(templateId, processDefinitionId, t);

        String XmlData = null;
        if (t instanceof BpmnModel) {
            BpmnXMLConverter converter = new BpmnXMLConverter();
            byte[] bpmnBytes = converter.convertToXML((BpmnModel)t);
            XmlData = new String(bpmnBytes);
        } else if (t instanceof String) {
            InputStream inputStream = ReflectUtil.getResourceAsStream((String)t);
            try {
                int length = inputStream.available();
                byte[] bpmnBytes = new byte[length];
                if (inputStream.read(bpmnBytes) == length) {
                    XmlData = new String(bpmnBytes);
                }
            } catch (IOException e) {
                log.error("读取流程定义资源文件失败", e);
            }
        }
        // 把 XmlData 更新到模板表
        ProcessTemplate template = new ProcessTemplate()
                .setTemplateId(templateId).setXmlData(XmlData);
        template.updateById();
    }
}
