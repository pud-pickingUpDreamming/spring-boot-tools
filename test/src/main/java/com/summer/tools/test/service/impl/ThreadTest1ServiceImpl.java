package com.summer.tools.test.service.impl;

import com.summer.tools.common.utils.JsonUtil;
import com.summer.tools.test.service.AbstractTheadTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("service1")
public class ThreadTest1ServiceImpl extends AbstractTheadTestService {

    @Override
    public void doJob() {

        log.info("实例1开始作业...");
        commonOpe();

        Map<String, Object> shareData = new HashMap<>();
        shareData.put(this.getClass().getSimpleName() + "::doJob", "doJob 开始");
        set(shareData);
        log.info(JsonUtil.stringify(shareData));
    }

    @Override
    public void doEcho() {

        log.info("实例1开始echo...");
        commonOpe();

        Map<String, Object> shareData = new HashMap<>();
        shareData.put(this.getClass().getSimpleName() + "::echo", "echo 开始");
        set(shareData);
        log.info(JsonUtil.stringify(shareData));
    }
}
