package org.flowable.ui.modeler.listeners;


import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.task.api.Task;
import org.flowable.ui.modeler.constants.ProcessConstants;
import org.flowable.ui.modeler.utils.ApplicationContextUtil;

/**
 * 流程(sequenceFlow)开始监听器
 */
@Slf4j
public class StartSequenceListener implements IElementListener {

    @Override
    public void notify(DelegateExecution execution) {

        TaskService taskService = ApplicationContextUtil.getBean(TaskService.class);

//        execution.setVariable();
        Task task = taskService.createTaskQuery().processInstanceId(execution.getCurrentActivityId()).active().singleResult();

        log.info("步骤[{}]进入执行开始监听器:执行id[{}],事件名称[{}],流程定义id[{}]", ProcessConstants.SEQUENCE_MONITOR.addAndGet(1),
                execution.getId(), execution.getEventName(), execution.getCurrentActivityId());
    }
}
