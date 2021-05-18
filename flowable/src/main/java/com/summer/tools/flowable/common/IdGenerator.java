package com.summer.tools.flowable.common;

import com.summer.tools.flowable.constants.TemplateConstants;

import java.util.UUID;

public class IdGenerator {
    public static String generateId(TemplateConstants.IdPrefixEnum prefix) {
        return prefix.getValue() + UUID.randomUUID().toString().replace("-", "");
    }
}
