package com.summer.tools.flowable.constants;

import com.summer.tools.flowable.listeners.EndTaskListener;
import com.summer.tools.flowable.listeners.ProcessStartListener;
import com.summer.tools.flowable.listeners.StartTaskListener;
import lombok.Getter;

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

    @Getter
    enum ProcessNodeTypeEnum implements ProcessConstants{
        START_NODE(1),
        END_NODE(2),
        USER_TASK(3),
        SERVICE_TASK(4),
        EXCLUSIVE_GATEWAY(5),
        PARALLEL_GATEWAY(6);

        private final int value;

        ProcessNodeTypeEnum(Integer value){
            this.value = value;
        }
    }

    @Getter
    enum ProcessListenerTypeEnum implements ProcessConstants {
        PROCESS_START(1, "processStart", ProcessStartListener.class),
        TASK_START(2, "taskStart", StartTaskListener.class),
        TASK_END(3, "taskEnd", EndTaskListener.class);

        ProcessListenerTypeEnum(Integer value, String name, Class<?> listener) {
            this.value = value;
            this.name = name;
            this.listener = listener;
        }

        private final int value;
        private final String name;
        private final Class<?> listener;
        private final String implementationType = "class";
    }
}
