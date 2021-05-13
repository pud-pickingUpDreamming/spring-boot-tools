package com.summer.tools.flowable.common;

import com.summer.tools.flowable.constants.ProcessConstants;

import java.util.UUID;

public class IdGenerator {
    public static String generateId(ProcessConstants.ProcessElementTypeEnum ele) {
        return ele.getValue() + UUID.randomUUID().toString().replace("-", "");
    }
}
