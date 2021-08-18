package org.flowable.ui.modeler.listeners;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.TaskService;
import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.ui.modeler.constants.ProcessConstants;
import org.flowable.ui.modeler.utils.ApplicationContextUtil;

/**
 * 完成流程
 */
@Slf4j
public class CreateTaskListener implements IElementListener {

    TaskService taskService = ApplicationContextUtil.getBean(TaskService.class);

    @Override
    public void notify(DelegateTask delegateTask) {

        if (ProcessConstants.ProcessTaskTag.NOTICE.getValue().equals(delegateTask.getCategory())) {
            taskService.complete(delegateTask.getId());
        } else if (ProcessConstants.ProcessTaskTag.APPROVE.getValue().equals(delegateTask.getCategory())){
            log.info(delegateTask.getCategory());
        } else {
            log.info("do something");
        }

        log.info("步骤[{}]进入创建任务监听器:任务id[{}],任务名称[{}],任务定义key[{}],操作人[{}]", ProcessConstants.SEQUENCE_MONITOR.addAndGet(1),
                delegateTask.getId(), delegateTask.getName(), delegateTask.getTaskDefinitionKey(), delegateTask.getAssignee());
    }
}
