package org.flowable.ui.modeler.listeners;


import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;

/**
 * 流程(sequenceFlow)开始监听器
 */
@Slf4j
public class StartSequenceListener implements IElementListener {

    @Override
    public void notify(DelegateExecution execution) {
    }
}
