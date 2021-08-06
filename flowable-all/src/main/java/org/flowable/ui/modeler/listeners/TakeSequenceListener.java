package org.flowable.ui.modeler.listeners;


import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.task.api.Task;
import org.flowable.ui.modeler.constants.ProcessConstants;
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

        log.info("步骤[{}]进入执行取任务监听器:执行id[{}],事件名称[{}],流程定义id[{}]", ProcessConstants.SEQUENCE_MONITOR.addAndGet(1),
                execution.getId(), execution.getEventName(), execution.getCurrentActivityId());

        tasks.forEach(f -> {
            if ("hr".equals(f.getCategory())) {
                taskService.complete(f.getId());
            }
        });
    }
}
