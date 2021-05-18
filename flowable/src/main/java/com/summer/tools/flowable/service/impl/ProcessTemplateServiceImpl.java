package com.summer.tools.flowable.service.impl;

import com.summer.tools.common.utils.Assert;
import com.summer.tools.flowable.common.AbstractProcessManager;
import com.summer.tools.flowable.common.IdGenerator;
import com.summer.tools.flowable.constants.ProcessConstants;
import com.summer.tools.flowable.constants.TemplateConstants;
import com.summer.tools.flowable.orm.model.DeployModel;
import com.summer.tools.flowable.orm.model.ProcessTemplate;
import com.summer.tools.flowable.service.IProcessLineService;
import com.summer.tools.flowable.service.IProcessNodeService;
import com.summer.tools.flowable.service.IProcessTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.util.ReflectUtil;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class ProcessTemplateServiceImpl extends AbstractProcessManager implements IProcessTemplateService {

    @Resource
    private IProcessNodeService nodeService;
    @Resource
    private IProcessLineService lineService;

    @Override
    public ProcessDefinition deploy(String processName, String resource) {
        String templateId = IdGenerator.generateId(TemplateConstants.IdPrefixEnum.TEMPLATE);
        new ProcessTemplate().setTemplateId(templateId).setType(ProcessConstants.ProcessTypeEnum.APPROVE.getValue())
                .setStatus(TemplateConstants.TemplateStatusEnum.DRAFT.getValue())
                .setName(processName).setDescription(processName + PROCESS_POSTFIX)
                .insert();
        return super.deploy(templateId, processName, resource);
    }

    @Override
    public ProcessDefinition deploy(DeployModel deployModel) {
        // 校验参数表达式
        boolean expression = StringUtils.isNoneBlank(deployModel.getProcessName())
                && CollectionUtils.isEmpty(deployModel.getProcessLines())
                && CollectionUtils.isEmpty(deployModel.getProcessNodes());
        Assert.noBusinessExceptions(expression, HttpStatus.BAD_REQUEST);

        String templateId = IdGenerator.generateId(TemplateConstants.IdPrefixEnum.TEMPLATE);
        new ProcessTemplate().setTemplateId(templateId).setType(ProcessConstants.ProcessTypeEnum.APPROVE.getValue())
                .setStatus(TemplateConstants.TemplateStatusEnum.DRAFT.getValue())
                .setName(deployModel.getProcessName()).setDescription(deployModel.getProcessName() + PROCESS_POSTFIX)
                .insert();
        deployModel.setTemplateId(templateId);
        // 记录节点
        this.nodeService.saveBatch(deployModel.getProcessNodes());

        // 记录线条
        this.lineService.saveBatch(deployModel.getProcessLines());

        // 部署
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
