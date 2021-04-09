package com.alibaba.repeater.console.start.config;

import com.alibaba.jvm.sandbox.repeater.plugin.core.util.LogUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.exception.BizException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BizException.class})
    public RepeaterResult<String> handle(BizException e) {
        LogUtil.info("异常信息：message:{}, e:{}", e.getMessage(), e);
        return RepeaterResult.builder().success(true).data(e.getMessage()).build();
    }

    @ExceptionHandler({Exception.class})
    public RepeaterResult<String> handle(Exception e) {
        LogUtil.info("异常信息：message:{}, e:{}", e.getMessage(), e);
        return RepeaterResult.builder().success(true).data("操作失败").build();
    }
}