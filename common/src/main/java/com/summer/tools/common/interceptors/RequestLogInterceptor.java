package com.summer.tools.common.interceptors;

import com.summer.tools.common.annotations.BackendOperation;
import com.summer.tools.common.constants.CommonConstants;
import com.summer.tools.common.model.OperationLog;
import com.summer.tools.common.services.IOperationLogService;
import com.summer.tools.common.utils.DateUtil;
import com.summer.tools.common.utils.IPUtil;
import com.summer.tools.common.utils.JsonUtil;
import com.summer.tools.common.utils.LocationUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author john.wang
 */
@Aspect
@Component
@Slf4j
@Order(-1)
public class RequestLogInterceptor {

    @Resource
    private HttpServletRequest request;
    /**
     * 公共功能只有接口,没有实现
     */
    @Resource
    private IOperationLogService operationLogService;

    /**
     * 日志模板,统一了方便ELK处理
     * 5个占位符分别代表 请求唯一标识,请求入参,请求结果,请求耗时
     */
    private static final String LOG_FORMAT = "key:{},requestArgs:{},response:{},ip:{},location{},cost:{}";

    @Pointcut("!@annotation(com.summer.tools.common.annotations.BackendOperation) " +
            "&& execution(public * com.summer.tools.*.controller..*Controller.*(..))")
    public void apiLogInterceptor() {
    }

    @Pointcut("@annotation(com.summer.tools.common.annotations.BackendOperation)")
    public void backendOperationLogInterceptor() {
    }

    /**
     * 触点端日志切面
     */
    @Around("apiLogInterceptor()")
    public Object apiLogInterceptor(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        long startTime = System.currentTimeMillis();

        String requestArgs = cleanArgs(pjp);
        String ip = IPUtil.getIpAddr(request);
        String location = LocationUtil.getLocationByIP(ip);

        try {
            result = pjp.proceed();
        } catch (Throwable ex) {
            log.error(LOG_FORMAT, request.getHeader(CommonConstants.REQUEST_KEY), requestArgs, null, ip, location, 0, ex);
            throw ex;
        } finally {
            String cost = getCostTime(startTime);
            String response = JsonUtil.stringify(result);


            log.info(LOG_FORMAT, request.getHeader(CommonConstants.REQUEST_KEY), requestArgs, response, ip, location, cost);
        }
        return result;
    }

    /**
     * 后台操作切面日志
     */
    @Around("backendOperationLogInterceptor()")
    public Object backendOperationLogInterceptor(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        OperationLog operationLog = new OperationLog();
        long startTime = System.currentTimeMillis();

        String requestArgs = cleanArgs(pjp);
        try {
            //获取类的字节码对象，通过字节码对象获取方法信息
            Class<?> targetCls = pjp.getTarget().getClass();
            //获取方法签名(通过此签名获取目标方法信息)
            MethodSignature ms = (MethodSignature)pjp.getSignature();
            //获取目标方法上的注解指定的操作名称
            Method targetMethod = targetCls.getDeclaredMethod(ms.getName(), ms.getParameterTypes());
            BackendOperation operation = targetMethod.getAnnotation(BackendOperation.class);
            operationLog.setRequestArgs(requestArgs)
                    .setModule(operation.module()).setFunction(operation.function());

            String ip = IPUtil.getIpAddr(request);
            String location = LocationUtil.getLocationByIP(ip);
            operationLog.setIpAddr(ip).setLocation(location)
                    .setUserName(request.getHeader(CommonConstants.CURRENT_USER_NAME))
                    .setUserId(request.getHeader(CommonConstants.CURRENT_USER_ID));

            result = pjp.proceed();
        } catch (Throwable ex) {
            log.error(JsonUtil.stringify(operationLog), ex);
            throw ex;
        } finally {
            String cost = getCostTime(startTime);
            String response = JsonUtil.stringify(result);

            operationLog.setResponse(response).setCost(cost)
                    .setCreateTime(DateTimeFormatter.ofPattern(DateUtil.YEAR_MONTH_DATE_HOUR_MINUTE_SECOND).format(LocalDateTime.now()));
            this.operationLogService.saveLog(operationLog);
            log.info(JsonUtil.stringify(operationLog));
        }
        return result;
    }

    private String cleanArgs(ProceedingJoinPoint pjp) {
        List<Object> args = new ArrayList<>();
        for (Object obj : pjp.getArgs()) {
            if (!(obj instanceof MultipartFile)) {
                args.add(obj);
            }
        }
        return JsonUtil.stringify(args);
    }

    private String getCostTime(long startTime) {
        long endTime = System.currentTimeMillis();
        long duration = endTime -startTime;
        int cost = duration > 10000 ? (int)duration/1000 : (int)duration;
        String unit = duration > 10000 ? "s" : "ms";
        return cost + unit;
    }
}
