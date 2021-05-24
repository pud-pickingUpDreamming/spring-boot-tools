package com.summer.tools.flowable.listeners;


import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;

/**
 * 流程(sequenceFlow)启用监听器
 */
@Slf4j
public class TakeSequenceListener implements IElementListener {

    @Override
    public void notify(DelegateExecution execution) {
    }
}
