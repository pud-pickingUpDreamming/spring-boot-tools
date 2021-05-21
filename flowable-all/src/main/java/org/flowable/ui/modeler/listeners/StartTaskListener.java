package org.flowable.ui.modeler.listeners;


import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * 任务开始监听器
 */
@Slf4j
public class StartTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
    }
}
