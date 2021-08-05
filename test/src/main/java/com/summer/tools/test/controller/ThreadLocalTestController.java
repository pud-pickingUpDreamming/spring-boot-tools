package com.summer.tools.test.controller;

import com.summer.tools.test.service.AbstractTheadTestService;
import com.summer.tools.test.service.ThreadTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class ThreadLocalTestController {

    @Resource
    private Map<String, ThreadTestService> testServiceMap;

    @GetMapping("/echo")
    public void echo() {
        testServiceMap.values().forEach(ThreadTestService::doEcho);
        AbstractTheadTestService.remove();
    }

    @GetMapping("/job")
    public void doJob() {
        testServiceMap.values().forEach(ThreadTestService::doJob);
        AbstractTheadTestService.remove();
    }
}
