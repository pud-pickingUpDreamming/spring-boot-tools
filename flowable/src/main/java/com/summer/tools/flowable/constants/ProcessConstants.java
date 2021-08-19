package com.summer.tools.flowable.constants;

import com.summer.tools.flowable.listeners.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface ProcessConstants {

    @Getter
    enum ProcessTypeEnum implements ProcessConstants{
        APPROVE(1, "审批");

        private final int value;
        private final String name;

        ProcessTypeEnum(Integer value, String name){
            this.value = value;
            this.name = name;
        }
    }

    /**
     * 流程监听顺序监控
     */
    AtomicInteger SEQUENCE_MONITOR = new AtomicInteger(0);

    /**
     * 节点标签, 用于控制节点需要执行哪些操作
     * NOTICE : 知会
     * APPROVE : 审批
     * TASK : 系统任务 需要绑定数据
     */
    @Getter
    enum ProcessNodeTag implements ProcessConstants {
        NOTICE("notice" , "通知"),
        APPROVE("approve" , "审批"),
        TASK("task" , "任务");

        ProcessNodeTag(String value, String name){
            this.value = value;
            this.name = name;
        }

        private final String value;
        private final String name;
    }

    @Getter
    enum ProcessNodeTypeEnum implements ProcessConstants{
        START_NODE(1, "start"),
        END_NODE(2, "end"),
        USER_TASK(3, "userTask"),
        SERVICE_TASK(4, "serviceTask"),
        EXCLUSIVE_GATEWAY(5, "exclusiveGateway"),
        PARALLEL_GATEWAY(6, "parallelGateway"),
        CATCH_EVENT(7, "catchEvent");

        private final int value;
        private final String name;

        ProcessNodeTypeEnum(Integer value, String name){
            this.value = value;
            this.name = name;
        }

        public static ProcessNodeTypeEnum getTypeEnum(Integer value) {
            return Arrays.stream(ProcessNodeTypeEnum.values()).filter(f->f.value == value).findFirst().orElse(null);
        }
    }

    @Getter
    enum ProcessListenerTypeEnum implements ProcessConstants {
        SEQUENCE_TASK_START(1, "start", "sequenceTask", new StartSequenceListener()),
        SEQUENCE_TASK_END(2, "end","sequenceTask", new EndSequenceListener()),
        SEQUENCE_TASK_TAKE(3, "take","sequenceTask", new TakeSequenceListener()),
        TASK_CREATE(4, "create","task", new CreateTaskListener()),
        TASK_ASSIGN(5, "assignment","task", new AssignTaskListener()),
        TASK_COMPLETE(6, "complete","task", new CompleteTaskListener()),
        TASK_DELETE(7, "delete","task", new DeleteTaskListener());

        ProcessListenerTypeEnum(Integer value, String type, String scope, IElementListener listener) {
            this.value = value;
            this.type = type;
            this.scope = scope;
            this.listener = listener;
        }

        public static List<ProcessListenerTypeEnum> getTypeEnums(String[] names) {
            List<ProcessListenerTypeEnum> typeEnums = new ArrayList<>();
            Arrays.asList(ProcessListenerTypeEnum.values()).forEach(f -> {
                if (Arrays.asList(names).contains(f.getType())) {
                    typeEnums.add(f);
                }
            });
            return typeEnums;
        }

        private final int value;
        private final String type;
        private final String scope;
        private final IElementListener listener;
        private final String implementationType = "class";
    }
}
