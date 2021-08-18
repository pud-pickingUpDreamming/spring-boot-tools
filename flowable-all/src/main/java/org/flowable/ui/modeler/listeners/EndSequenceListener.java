package org.flowable.ui.modeler.listeners;


import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.ui.modeler.constants.ProcessConstants;

/**
 * 流程(sequenceFlow)结束监听器
 */
@Slf4j
public class EndSequenceListener implements IElementListener {

    @Override
    public void notify(DelegateExecution execution) {

        log.info("步骤[{}]进入执行结束监听器:执行id[{}],事件名称[{}],当前活动线条id[{}]", ProcessConstants.SEQUENCE_MONITOR.addAndGet(1),
                execution.getId(), execution.getEventName(), execution.getCurrentActivityId());

    }
}
