package com.summer.tools.mybatisplus.runner;

import com.summer.tools.common.constants.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@Component
@Order(RunnerOrder.LOAD_DB_RESOURCE)
//@RequiredArgsConstructor(onConstructor = @__(@Resource))
public class LoadDbResourceRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		MDC.put(CommonConstants.TRACE_ID, UUID.randomUUID().toString().replace("-", ""));
	}
}
