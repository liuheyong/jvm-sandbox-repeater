package com.alibaba.repeater.console.start.controller.api;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeatModel;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.params.ReplayParams;
import com.alibaba.repeater.console.service.service.RecordService;
import com.alibaba.repeater.console.service.service.ReplayService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * {@link PersistenceFacadeApi} Demo工程；console为repeater提供的录制回放的相关接口
 *
 * @author zhaoyb1990
 */
@RestController
@RequestMapping("/facade/api/")
public class PersistenceFacadeApi {

    @Resource
    private RecordService recordService;
    @Resource
    private ReplayService replayService;

    /**
     * 手动触发回放
     *
     * @Author: liuheyong
     * @date: 2021/3/21
     */
    @PostMapping(value = "repeat")
    public RepeaterResult<String> repeat(@RequestBody ReplayParams param, HttpServletRequest request) {
        ReplayParams params = ReplayParams.builder().repeatId(request.getHeader("RepeatId")).build();
        params.setAppName(param.getAppName());
        params.setTraceId(param.getTraceId());
        if (StringUtils.isBlank(params.getIp())) {
            params.setIp(param.getIp());
        }
        return replayService.replay(params);
    }

    /**
     * 手动查看回放结果
     *
     * @Author: liuheyong
     * @date: 2021/3/21
     */
    @RequestMapping(value = "repeat/callback/{repeatId}", method = RequestMethod.GET)
    public RepeaterResult<RepeatModel> callback(@PathVariable("repeatId") String repeatId) {
        return recordService.callback(repeatId);
    }

    /**
     * 回放消息取record数据
     *
     * @return
     */
    @RequestMapping(value = "record/{appName}/{traceId}", method = RequestMethod.GET)
    public RepeaterResult<String> getWrapperRecord(@PathVariable("appName") String appName,
                                                   @PathVariable("traceId") String traceId) {
        return recordService.get(appName, traceId);
    }

    /**
     * 手动录制消息投递地址
     *
     * @return
     */
    @RequestMapping(value = "record/save", method = RequestMethod.POST)
    public RepeaterResult<String> recordSave(@RequestBody String body) {
        return recordService.saveRecord(body);
    }

    /**
     * 手动回放结果投递地址
     *
     * @return
     */
    @RequestMapping(value = "repeat/save", method = RequestMethod.POST)
    public RepeaterResult<String> repeatSave(@RequestBody String body) {
        return replayService.saveRepeat(body);
    }

}
