package com.alibaba.repeater.console.start.controller.page;

import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializeException;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.Behavior;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.ModuleConfigBO;
import com.alibaba.repeater.console.common.domain.PageResult;
import com.alibaba.repeater.console.common.params.ModuleConfigParams;
import com.alibaba.repeater.console.service.ModuleConfigService;
import com.alibaba.repeater.console.service.util.JacksonUtil;
import com.alibaba.repeater.console.start.controller.vo.PagerAdapter;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * {@link ModuleConfigController}
 * <p>
 * 配置管理页面
 *
 * @author zhaoyb1990
 */
@RequestMapping("/config")
@RestController
public class ModuleConfigController {

    @Resource
    private ModuleConfigService moduleConfigService;

    /**
     * 配置管理->列表接口
     *
     * @Author: liuheyong
     * @date: 2021/3/25
     */
    @PostMapping("/list")
    public RepeaterResult<PagerAdapter<ModuleConfigBO>> list(@RequestBody ModuleConfigParams params) {
        PageResult<ModuleConfigBO> result = moduleConfigService.list(params);
        return RepeaterResult.builder().success(true).data(PagerAdapter.transform(result)).build();
    }

    /**
     * 配置管理->详情接口
     *
     * @Author: liuheyong
     * @date: 2021/3/25
     */
    @PostMapping("/detail")
    public RepeaterResult<ModuleConfigBO> detail(@RequestBody ModuleConfigParams params) {
        RepeaterResult<ModuleConfigBO> result = moduleConfigService.query(params);
        if (!result.isSuccess()) {
            return RepeaterResult.builder().success(false).message("fail").build();
        }
        return result;
    }

    /**
     * 配置管理->新增、修改接口
     *
     * @Author: liuheyong
     * @date: 2021/3/25
     */
    @PostMapping("/saveOrUpdate")
    public RepeaterResult<ModuleConfigBO> saveOrUpdate(@RequestBody ModuleConfigParams params) {
        return moduleConfigService.saveOrUpdate(params);
    }

    /**
     * 配置管理->配置推送接口
     *
     * @Author: liuheyong
     * @date: 2021/3/25
     */
    @PostMapping("/push")
    public RepeaterResult<ModuleConfigBO> push(@RequestBody ModuleConfigParams params) {
        return moduleConfigService.push(params);
    }

    /**
     * 配置管理->新增时查询配置模板接口
     *
     * @Author: liuheyong
     * @date: 2021/3/25
     */
    @PostMapping("/add")
    public RepeaterResult<String> add() {
        RepeaterConfig defaultConf = new RepeaterConfig();
        List<Behavior> behaviors = Lists.newArrayList();
        defaultConf.setPluginIdentities(Lists.newArrayList("http", "java-entrance", "java-subInvoke"));
        defaultConf.setRepeatIdentities(Lists.newArrayList("java", "http"));
        defaultConf.setUseTtl(true);
        defaultConf.setHttpEntrancePatterns(Lists.newArrayList("^/regress/.*$"));
        behaviors.add(new Behavior("com.alibaba.repeater.console.service.impl.RegressServiceImpl", "getRegress"));
        defaultConf.setJavaEntranceBehaviors(behaviors);
        List<Behavior> subBehaviors = Lists.newArrayList();
        subBehaviors.add(new Behavior("com.alibaba.repeater.console.service.impl.RegressServiceImpl", "getRegressInner", "findPartner", "slogan"));
        defaultConf.setJavaSubInvokeBehaviors(subBehaviors);
        try {
            return RepeaterResult.builder().success(true).data(JacksonUtil.serialize(defaultConf)).build();
        } catch (SerializeException e) {
            return RepeaterResult.builder().success(false).message("fail").build();
        }
    }

}
