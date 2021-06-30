package com.summer.tools.mybatisplus.interceptor;

import com.summer.tools.common.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author john.wang
 */
@Aspect
@Component
@Slf4j
@Order(0)
public class TenantInterceptor {

    @Resource
    private HttpServletRequest request;

    @Pointcut("execution(public * com.summer.tools.mybatisplus.controller..*Controller.*(..))")
    public void tenantInterceptor() {
    }

    /**
     * 多租户切面
     */
    @Around("tenantInterceptor()")
    public Object tenantInterceptor(ProceedingJoinPoint pjp) throws Throwable {

        String tenant = request.getHeader(CommonConstants.TENANT);

        try {
//            ServiceDatasource
            return pjp.proceed();
        } catch (Throwable ex) {
            log.error("数据源切换失败", ex);
            throw ex;
        }
    }
}
