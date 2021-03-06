package com.alibaba.repeater.console.start.controller.api;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.ModuleConfigBO;
import com.alibaba.repeater.console.common.params.ModuleConfigParams;
import com.alibaba.repeater.console.service.service.ModuleConfigService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * {@link ConfigFacadeController} Demo工程；作为repeater录制回放的配置管理服务
 * <p>
 *
 * @author zhaoyb1990
 */
@RestController
@RequestMapping("/facade/api")
public class ConfigFacadeController {

    @Resource
    private ModuleConfigService moduleConfigService;

    /**
     * 手动获取配置
     * 在非 standalone 模式下，会从 repeater-console 的 /facade/api/config/${appName}/${env}接口中拉取配置。
     * 在 standalone 模式下则读取~/.sandbox-module/cfg/repeater-config.json下的配置
     *
     * @param appName
     * @param env
     * @return
     */
    @RequestMapping("/config/{appName}/{env}")
    public RepeaterResult<RepeaterConfig> getConfig(@PathVariable("appName") String appName,
                                                    @PathVariable("env") String env) {
        ModuleConfigParams params = new ModuleConfigParams();
        params.setAppName(appName);
        params.setEnvironment(env);
        RepeaterResult<ModuleConfigBO> result = moduleConfigService.query(params);
        return RepeaterResult.builder().success(result.isSuccess()).message(result.getMessage()).data(null == result.getData() ? null : result.getData().getConfigModel()).build();
    }
}
