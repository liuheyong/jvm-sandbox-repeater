package com.alibaba.repeater.console.start.controller.page;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.ModuleInfoBO;
import com.alibaba.repeater.console.common.domain.PageResult;
import com.alibaba.repeater.console.common.params.ModuleInfoParams;
import com.alibaba.repeater.console.service.ModuleInfoService;
import com.alibaba.repeater.console.start.controller.vo.PagerAdapter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * {@link ModuleInfoController}
 * <p>
 * 在线模块页面
 *
 * @author zhaoyb1990
 */
@CrossOrigin
@RestController
@RequestMapping("/module")
public class ModuleInfoController {

    @Resource
    private ModuleInfoService moduleInfoService;

    /**
     * 在线模块->模块列表接口
     *
     * @Author: liuheyong
     * @date: 2021/3/25
     */
    @PostMapping("/list")
    public RepeaterResult<PagerAdapter<ModuleInfoBO>> list(@RequestBody ModuleInfoParams params) {
        PageResult<ModuleInfoBO> result = moduleInfoService.query(params);
        return RepeaterResult.builder().success(true).data(PagerAdapter.transform(result)).build();
    }

    /**
     * 在线模块->安装模块接口
     *
     * @Author: liuheyong
     * @date: 2021/3/25
     */
    @PostMapping("/install")
    public RepeaterResult<String> install(@RequestBody ModuleInfoParams params) {
        return moduleInfoService.install(params);
    }

    /**
     * 在线模块->刷新接口
     *
     * @Author: liuheyong
     * @date: 2021/3/25
     */
    @PostMapping("/reload")
    public RepeaterResult<String> reload(@RequestBody ModuleInfoParams params) {
        return moduleInfoService.reload(params);
    }

    /**
     * 在线模块->冻结接口
     *
     * @Author: liuheyong
     * @date: 2021/3/25
     */
    @PostMapping("/frozen")
    public RepeaterResult<ModuleInfoBO> frozen(@RequestBody ModuleInfoParams params) {
        return moduleInfoService.frozen(params);
    }

    /**
     * 在线模块->激活接口
     *
     * @Author: liuheyong
     * @date: 2021/3/25
     */
    @PostMapping("/active")
    public RepeaterResult<ModuleInfoBO> active(@RequestBody ModuleInfoParams params) {
        return moduleInfoService.active(params);
    }

    /**
     * 根据appName查询list【非对接接口】
     *
     * @Author: liuheyong
     * @date: 2021/3/25
     */
    @RequestMapping("/byName.json")
    public RepeaterResult<List<ModuleInfoBO>> list(@RequestParam("appName") String appName) {
        return moduleInfoService.query(appName);
    }

    /**
     * 心跳上报配置【非对接接口】
     *
     * @return
     */
    @RequestMapping("/report.json")
    public RepeaterResult<ModuleInfoBO> list(@ModelAttribute("requestParams") ModuleInfoBO params) {
        return moduleInfoService.report(params);
    }

}
