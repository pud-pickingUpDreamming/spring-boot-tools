package com.summer.tools.flowable.constants;

import lombok.Getter;

public interface ProcessConstants {

    @Getter
    enum ProcessNodeTypeEnum implements ProcessConstants{
        START_NODE(1),
        END_NODE(2),
        USER_TASK(3),
        SERVICE_TASK(4),
        EXCLUSIVE_GATEWAY(5),
        PARALLEL_GATEWAY(6);

        private final int type;

        ProcessNodeTypeEnum(Integer type){
            this.type = type;
        }
    }

    @Getter
    enum ProcessListenerTypeEnum implements ProcessConstants {
        CREATE(1, "create"),
        ASSIGNMENT(2, "assignment"),
        COMPLETE(3, "complete"),
        DELETE(4, "delete"),
        START(5, "start"),
        TAKE(6, "take"),
        END(7, "end");

        ProcessListenerTypeEnum(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        private final int value;
        private final String name;
    }
}
