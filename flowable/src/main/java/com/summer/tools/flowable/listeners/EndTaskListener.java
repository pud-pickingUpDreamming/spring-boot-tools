package com.summer.tools.flowable.listeners;


import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * 任务结束监听器
 */
@Slf4j
public class EndTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("进入任务完成监听器，任务ID：{},节点：{}", delegateTask.getId(), delegateTask.getTaskDefinitionKey());
    }
}
