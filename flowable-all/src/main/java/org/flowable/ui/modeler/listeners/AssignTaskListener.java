package org.flowable.ui.modeler.listeners;

import lombok.extern.slf4j.Slf4j;
import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.ui.modeler.constants.ProcessConstants;

@Slf4j
public class AssignTaskListener implements IElementListener {

    @Override
    public void notify(DelegateTask delegateTask) {

        log.info("步骤[{}]进入分配任务执行人监听器:任务id[{}],任务名称[{}],任务定义key[{}],操作人[{}]", ProcessConstants.SEQUENCE_MONITOR.addAndGet(1),
                delegateTask.getId(), delegateTask.getName(), delegateTask.getTaskDefinitionKey(), delegateTask.getAssignee());
    }
}
