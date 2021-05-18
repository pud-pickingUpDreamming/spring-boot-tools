package com.summer.tools.flowable.constants;

import com.summer.tools.flowable.listeners.EndTaskListener;
import com.summer.tools.flowable.listeners.ProcessStartListener;
import com.summer.tools.flowable.listeners.StartTaskListener;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        START_NODE(1, "start"),
        END_NODE(2, "end"),
        USER_TASK(3, "userTask"),
        SERVICE_TASK(4, "serviceTask"),
        EXCLUSIVE_GATEWAY(5, "exclusiveGateway"),
        PARALLEL_GATEWAY(6, "parallelGateway");

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
        PROCESS_START(1, "processStart", ProcessStartListener.class),
        TASK_START(2, "taskStart", StartTaskListener.class),
        TASK_END(3, "taskEnd", EndTaskListener.class);

        ProcessListenerTypeEnum(Integer value, String name, Class<?> listener) {
            this.value = value;
            this.name = name;
            this.listener = listener;
        }

        public static List<ProcessListenerTypeEnum> getTypeEnums(String[] names) {
            List<ProcessListenerTypeEnum> typeEnums = new ArrayList<>();
            Arrays.asList(ProcessListenerTypeEnum.values()).forEach(f -> {
                if (Arrays.asList(names).contains(f.getName())) {
                    typeEnums.add(f);
                }
            });
            return typeEnums;
        }

        private final int value;
        private final String name;
        private final Class<?> listener;
        private final String implementationType = "class";
    }
}
