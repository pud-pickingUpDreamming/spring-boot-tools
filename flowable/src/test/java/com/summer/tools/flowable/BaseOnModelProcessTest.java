package com.summer.tools.flowable;

import com.summer.tools.flowable.service.IProcessTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
public class BaseOnModelProcessTest {

    @Resource
    private IProcessTemplateService flowService;

    @Test
    public void deploy() {
        ProcessDefinition processDefinition = this.flowService.deploy("1","expense", "processes/expense.bpmn20.xml");
    }

    @Test
    public void deployBaseOnModel() {

    }
}
