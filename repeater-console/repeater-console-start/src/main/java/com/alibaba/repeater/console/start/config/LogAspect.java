package com.alibaba.repeater.console.start.config;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.LogUtil;
import com.alibaba.repeater.console.common.enums.RequestLayerEnum;
import com.alibaba.repeater.console.common.params.AopLog;
import com.alibaba.repeater.console.service.util.IpUtil;
import com.google.common.collect.Maps;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @description: aop日志打印
 */
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(public * com.alibaba.repeater.console.start.controller..*Controller.*(..))")
    public void controllerLog() {
    }

    @Around("controllerLog()")
    public Object controllerAroundLog(ProceedingJoinPoint point) throws Throwable {
        return commonPrintLog(RequestLayerEnum.CONTROLLER, point);
    }

    /**
     * 切面日志打印公共方法
     *
     * @param layerEnum
     * @param point
     * @throws Throwable
     */
    public Object commonPrintLog(RequestLayerEnum layerEnum, ProceedingJoinPoint point) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        // 打印请求相关参数
        long startTime = System.currentTimeMillis();
        String header = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        AopLog log = new AopLog();
        log.setThreadId(Long.toString(Thread.currentThread().getId()));
        log.setThreadName(Thread.currentThread().getName());
        log.setIp(IpUtil.getIp(request));
        log.setHttpMethod(request.getMethod());
        log.setClassMethod(String.format("%s.%s()", point.getSignature().getDeclaringTypeName(), point.getSignature().getName()));
        Map<String, Object> nameAndValue = getNameAndValue(point);
        log.setOs(userAgent.getOperatingSystem().toString());
        log.setBrowser(userAgent.getBrowser().toString());
        log.setUserAgent(header);

        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\nrequest-layer:【").append(layerEnum.getName()).append("】\n")
                .append("request-info: ").append(JSONUtil.toJsonStr(log)).append("\n")
                .append("request-url: ").append(request.getRequestURL().toString()).append("\n")
                .append("request-param: ").append(JSONUtil.toJsonStr(nameAndValue)).append("\n");
        Object result = null;
        try {
            result = point.proceed();
        } catch (Throwable throwable) {
            result = throwable.getMessage();
            throw throwable;
        } finally {
            logBuilder.append("response-result: ").append(JSONUtil.toJsonStr(result)).append("\n")
                    .append("response-cost: ").append(System.currentTimeMillis() - startTime).append(" ms!\n");
            LogUtil.info("请求信息：" + logBuilder.toString());
        }
        return result;
    }

    /**
     * 获取方法参数名和参数值
     *
     * @param joinPoint
     * @return
     */
    private Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {
        final Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        final String[] names = methodSignature.getParameterNames();
        final Object[] args = joinPoint.getArgs();
        if (ArrayUtil.isEmpty(names) || ArrayUtil.isEmpty(args)) {
            return Collections.emptyMap();
        }
        if (names.length != args.length) {
            LogUtil.warn("{}方法参数名和参数值数量不一致", methodSignature.getName());
            return Collections.emptyMap();
        }
        Map<String, Object> map = Maps.newHashMap();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], args[i]);
        }
        return map;
    }
}
