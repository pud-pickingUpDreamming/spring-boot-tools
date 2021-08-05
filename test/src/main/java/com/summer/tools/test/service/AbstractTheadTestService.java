package com.summer.tools.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Slf4j
public abstract class AbstractTheadTestService implements ThreadTestService {

    private static final ThreadLocal<Map<String, Object>> SHARE_DATA = new ThreadLocal<>();

    @Override
    public void commonOpe() {
        log.info("this is a common operate method");
    }

    protected void set(Map<String, Object> data) {
        if (ObjectUtils.isEmpty(SHARE_DATA.get())) {
            SHARE_DATA.set(data);
        } else {
            SHARE_DATA.get().putAll(data);
        }
    }

    protected Map<String, Object> get() {
        return SHARE_DATA.get();
    }

    public static void remove() {
        SHARE_DATA.remove();
    }
}
