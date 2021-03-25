package com.alibaba.repeater.console.start.controller.page;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.PageResult;
import com.alibaba.repeater.console.common.domain.RecordBO;
import com.alibaba.repeater.console.common.domain.RecordDetailBO;
import com.alibaba.repeater.console.common.params.RecordParams;
import com.alibaba.repeater.console.service.RecordService;
import com.alibaba.repeater.console.start.controller.vo.PagerAdapter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * {@link OnlineController}
 * <p>
 * 在线流量页面
 *
 * @author zhaoyb1990
 */
@RestController
@RequestMapping("/online")
public class OnlineController {

    @Resource
    private RecordService recordService;

    /**
     * 在线列表接口
     *
     * @Author: liuheyong
     * @date: 2021/3/25
     */
    @RequestMapping("/online_list")
    public RepeaterResult<PagerAdapter<RecordBO>> search(@RequestBody RecordParams params) {
        PageResult<RecordBO> result = recordService.query(params);
        return RepeaterResult.builder().success(true).data(PagerAdapter.transform(result)).build();
    }

    /**
     * 在线详情接口
     *
     * @Author: liuheyong
     * @date: 2021/3/25
     */
    @RequestMapping("/online_detail")
    public RepeaterResult<RecordDetailBO> detail(@RequestBody RecordParams params) {
        RepeaterResult<RecordDetailBO> result = recordService.getDetail(params);
        if (!result.isSuccess()) {
            return RepeaterResult.builder().success(false).message("fail").build();
        }
        return result;
    }
}
