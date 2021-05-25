package org.flowable.ui.modeler.listeners;


import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.task.api.Task;
import org.flowable.ui.modeler.utils.ApplicationContextUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;
import java.util.List;

/**
 * 流程(sequenceFlow)启用监听器
 */
@Slf4j
public class TakeSequenceListener implements IElementListener {

    @Override
    public void notify(DelegateExecution execution) {
        TaskService taskService = ApplicationContextUtil.getBean(TaskService.class);
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(execution.getProcessInstanceId()).list();
        tasks.forEach(f -> {
            if ("hr".equals(f.getCategory())) {
                taskService.complete(f.getId());
            }
        });
    }
}
