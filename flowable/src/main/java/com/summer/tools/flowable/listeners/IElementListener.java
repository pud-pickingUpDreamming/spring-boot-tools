package com.summer.tools.flowable.listeners;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

public interface IElementListener extends ExecutionListener, TaskListener {

    @Override
    default void notify(DelegateExecution execution) {}
    @Override
    default void notify(DelegateTask delegateTask) {}
}
