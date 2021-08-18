package org.flowable.ui.modeler.listeners;

import com.summer.tools.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.ui.modeler.constants.ProcessConstants;

import java.util.Map;

/**
 * 设置下一步参数
 */
@Slf4j
public class CompleteTaskListener implements IElementListener {

    @Override
    public void notify(DelegateTask delegateTask) {

        log.info("步骤[{}]进入任务完成监听器:任务id[{}],任务名称[{}],任务定义key[{}],操作人[{}]", ProcessConstants.SEQUENCE_MONITOR.addAndGet(1),
                delegateTask.getId(), delegateTask.getName(), delegateTask.getTaskDefinitionKey(), delegateTask.getAssignee());

        if (ProcessConstants.ProcessTaskTag.NOTICE.getValue().equals(delegateTask.getCategory())) {
            Map<String, Object> vars = delegateTask.getVariables();
            log.info(JsonUtil.stringify(vars));
        } else if (ProcessConstants.ProcessTaskTag.APPROVE.getValue().equals(delegateTask.getCategory())){
            log.info(delegateTask.getCategory());
        } else {
            log.info("do something");
        }
    }
}
