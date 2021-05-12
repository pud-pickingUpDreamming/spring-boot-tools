package com.summer.tools.flowable.listeners;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;


/**
 * 流程启动监听器
 */
@Slf4j
public class ProcessStartListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) {}
}