package com.alibaba.repeater.console.start.controller.test;

import com.alibaba.jvm.sandbox.repeater.plugin.core.util.LogUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: liuheyng
 * @date: 2021/3/18 16:09
 * @description:
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String say() {
        LogUtil.info("============= Hello springboot =============");
        return "hello,this is a springboot demo！~";
    }
}
