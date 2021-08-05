package com.summer.tools.test.service.impl;

import com.summer.tools.common.utils.JsonUtil;
import com.summer.tools.test.service.AbstractTheadTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service("service2")
public class ThreadTest2ServiceImpl extends AbstractTheadTestService {

    @Override
    public void doJob() {

        log.info("实例2开始作业...");
        commonOpe();

        Map<String, Object> shareData = get();
        log.info(JsonUtil.stringify(shareData));
    }

    @Override
    public void doEcho() {

        log.info("实例2开始echo...");
        commonOpe();

        Map<String, Object> shareData = get();
        log.info(JsonUtil.stringify(shareData));
    }
}
