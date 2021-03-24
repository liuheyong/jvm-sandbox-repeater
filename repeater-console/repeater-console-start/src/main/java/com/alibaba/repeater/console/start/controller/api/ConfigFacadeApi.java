package com.alibaba.repeater.console.start.controller.api;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.ModuleConfigBO;
import com.alibaba.repeater.console.common.params.ModuleConfigParams;
import com.alibaba.repeater.console.service.ModuleConfigService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * {@link ConfigFacadeApi} Demo工程；作为repeater录制回放的配置管理服务
 * <p>
 *
 * @author zhaoyb1990
 */
@RestController
@RequestMapping("/facade/api")
public class ConfigFacadeApi {

    @Resource
    private ModuleConfigService moduleConfigService;

    /**
     * 手动获取配置文件
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
        /// 改为了可以适用于 gs-rest-service 的配置
        //RepeaterConfig config = new RepeaterConfig();
        //List<Behavior> behaviors = Lists.newArrayList();
        //config.setPluginIdentities(Lists.newArrayList("http", "java-entrance", "java-subInvoke", "mybatis", "ibatis"));
        //// 回放器
        //config.setRepeatIdentities(Lists.newArrayList("java", "http"));
        //// 白名单列表
        //config.setHttpEntrancePatterns(Lists.newArrayList("^/greeting.*$"));
        //// java入口方法
        //behaviors.add(new Behavior("hello.GreetingController", "greeting"));
        //config.setJavaEntranceBehaviors(behaviors);
        //List<Behavior> subBehaviors = Lists.newArrayList();
        // java调用插件
        //config.setJavaSubInvokeBehaviors(subBehaviors);
        //config.setUseTtl(true);
        //return RepeaterResult.builder().success(true).message("operate success").data(config).build();
    }
}
