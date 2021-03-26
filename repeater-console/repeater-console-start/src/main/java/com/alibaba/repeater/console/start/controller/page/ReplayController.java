package com.alibaba.repeater.console.start.controller.page;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.ReplayBO;
import com.alibaba.repeater.console.common.params.ReplayParams;
import com.alibaba.repeater.console.service.ReplayService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * {@link ReplayController}
 * <p></>
 * 回放相关接口
 *
 * @author zhaoyb1990
 */
@RestController
@RequestMapping("/replay")
public class ReplayController {

    @Resource
    private ReplayService replayService;

    /**
     * 回放详情接口
     *
     * @Author: liuheyong
     * @date: 2021/3/25
     */
    @PostMapping("/replay_detail")
    public RepeaterResult<ReplayBO> detail(@RequestBody ReplayParams params) {
        RepeaterResult<ReplayBO> result = replayService.query(params);
        if (!result.isSuccess()) {
            return RepeaterResult.builder().success(false).message("fail").build();
        }
        return result;
    }

    /**
     * 回放接口
     *
     * @Author: liuheyong
     * @date: 2021/3/25
     */
    @PostMapping("/do_replay")
    public RepeaterResult<String> replay(@Validated @RequestBody ReplayParams params) {
        return replayService.replay(params);
    }
}
