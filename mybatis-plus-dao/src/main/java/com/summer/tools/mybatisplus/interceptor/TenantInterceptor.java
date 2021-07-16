package com.summer.tools.mybatisplus.interceptor;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.summer.tools.common.constants.CommonConstants;
import com.summer.tools.mybatisplus.config.DataSourceProvider;
import com.summer.tools.mybatisplus.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Map;

/**
 * 基于mybatis-plus的多租户切面
 * @author john.wang
 */
@Aspect
@Component
@Slf4j
@Order(0)
public class TenantInterceptor {

    @Resource
    private HttpServletRequest request;
    @Resource
    private DataSourceProvider dataSourceProvider;

    @Value("${tenant.service}")
    private String service;

    @Pointcut("execution(public * com.summer.tools.mybatisplus.controller..*Controller.*(..))")
    public void tenantInterceptor() {
    }

    /**
     * 多租户切面
     */
    @Around("tenantInterceptor()")
    public Object tenantInterceptor(ProceedingJoinPoint pjp) throws Throwable {

        String tenant = request.getHeader(CommonConstants.TENANT);
        Map<String, DataSourceProperty> dataSourcePropertyMap = this.dataSourceProvider.getDataSourcePropertiesMap();

        // 如果请求头没有传租户信息或者没有没有配置多数据源,使用默认数据源
        if(StringUtils.isBlank(tenant) || dataSourcePropertyMap.size() == 0) {
            return proxyMethodRunning(pjp);
        }

        // 获取被请求的方法
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        Method method = signature.getMethod();

        // 获取 忽略数据源切换 注解
        DS ignoreDataSource = method.getAnnotation(DS.class);
        DS classIgnoreDataSource = method.getDeclaringClass().getAnnotation(DS.class);
        if(ignoreDataSource != null || classIgnoreDataSource != null){
            return proxyMethodRunning(pjp);
        }

        try {
            DynamicDataSourceContextHolder.push(MessageFormat.format(Constants.DS_KEY, tenant, service));

            return proxyMethodRunning(pjp);
        } catch (Throwable ex) {
            log.error("数据源切换失败", ex);
            throw ex;
        }
    }
    /**
     * 继续执行被代理的方法
     * @param pjp 连接点
     * @return  响应结果
     * @throws Throwable 系统运行异常
     */
    private Object proxyMethodRunning(ProceedingJoinPoint pjp) throws Throwable{
        try {
            return pjp.proceed();
        }finally {
            DynamicDataSourceContextHolder.clear();
        }
    }

}
