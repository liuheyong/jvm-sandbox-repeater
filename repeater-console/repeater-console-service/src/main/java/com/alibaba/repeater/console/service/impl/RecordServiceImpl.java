package com.alibaba.repeater.console.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.core.wrapper.RecordWrapper;
import com.alibaba.jvm.sandbox.repeater.plugin.core.wrapper.SerializerWrapper;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeatModel;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.constant.Constant;
import com.alibaba.repeater.console.common.domain.PageResult;
import com.alibaba.repeater.console.common.domain.RecordBO;
import com.alibaba.repeater.console.common.domain.RecordDetailBO;
import com.alibaba.repeater.console.common.params.RecordParams;
import com.alibaba.repeater.console.dal.dao.RecordDao;
import com.alibaba.repeater.console.dal.model.Record;
import com.alibaba.repeater.console.service.RecordService;
import com.alibaba.repeater.console.service.convert.ModelConverter;
import com.alibaba.repeater.console.service.util.ConvertUtil;
import com.alibaba.repeater.console.service.util.EsUtil;
import com.alibaba.repeater.console.service.util.ResultHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * {@link RecordServiceImpl} 使用mysql实现存储
 * <p>
 *
 * @author zhaoyb1990
 */
@Service("recordService")
@Slf4j
public class RecordServiceImpl implements RecordService {

    @Resource
    private RecordDao recordDao;
    @Resource
    private ModelConverter<Record, RecordBO> recordConverter;
    @Resource
    private ModelConverter<Record, RecordDetailBO> recordDetailConverter;
    @Resource
    private EsUtil esUtil;

    @Override
    public RepeaterResult<String> saveRecord(String body) {
        try {
            RecordWrapper wrapper = SerializerWrapper.hessianDeserialize(body, RecordWrapper.class);
            if (wrapper == null || StringUtils.isEmpty(wrapper.getAppName())) {
                return RepeaterResult.builder().success(false).message("invalid request").build();
            }
            Record record = ConvertUtil.convertWrapper(wrapper, body);
            esUtil.save(Constant.ES_INDEX, Constant.RECORD_ES_TYPE, record.getGmtRecord(), record);
            return RepeaterResult.builder().success(true).message("operate success").data("-/-").build();
        } catch (Throwable throwable) {
            return RepeaterResult.builder().success(false).message(throwable.getMessage()).build();
        }
    }

    @Override
    public RepeaterResult<String> get(String appName, String traceId) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .timeout(new TimeValue(5, TimeUnit.SECONDS))
                .query(QueryBuilders.termsQuery("appName", appName))
                .query(QueryBuilders.termsQuery("traceId", traceId))
                .sort(new ScoreSortBuilder().order(SortOrder.DESC));
        List<Map<String, Object>> search = esUtil.search(Constant.ES_INDEX, Constant.MODULE_INFO_ES_TYPE, sourceBuilder);
        List<Record> objectList = search.stream()
                .map(o -> BeanUtil.mapToBean(o, Record.class, true))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(objectList) || objectList.get(0) == null) {
            return ResultHelper.fail("data not exist");
        }
        return RepeaterResult.builder().success(true).message("operate success").data(objectList.get(0).getWrapperRecord()).build();
    }

    @Override
    public PageResult<RecordBO> query(RecordParams params) {
        if (!esUtil.indexExists(Constant.ES_INDEX)) {
            PageResult<RecordBO> pageResult = new PageResult<>();
            pageResult.setSuccess(false);
            pageResult.setMessage("no such data: " + Constant.ES_INDEX);
            return pageResult;
        }
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .from((params.getPage() - 1) * params.getSize())
                .size(params.getSize())
                .timeout(new TimeValue(5, TimeUnit.SECONDS))
                .sort(new ScoreSortBuilder().order(SortOrder.DESC));
        if (StringUtils.isNotBlank(params.getAppName())) {
            sourceBuilder.query(QueryBuilders.termsQuery("appName", params.getAppName()));
        }
        if (StringUtils.isNotBlank(params.getTraceId())) {
            sourceBuilder.query(QueryBuilders.termsQuery("traceId", params.getTraceId()));
        }
        List<Map<String, Object>> search = esUtil.search(Constant.ES_INDEX, Constant.RECORD_ES_TYPE, sourceBuilder);
        List<Record> objectList = search.stream()
                .map(o -> BeanUtil.mapToBean(o, Record.class, true))
                .collect(Collectors.toList());

        SearchSourceBuilder sourceBuilder2 = new SearchSourceBuilder()
                .timeout(new TimeValue(5, TimeUnit.SECONDS))
                .sort(new ScoreSortBuilder().order(SortOrder.DESC));
        List<Map<String, Object>> search2 = esUtil.search(Constant.ES_INDEX, Constant.RECORD_ES_TYPE, sourceBuilder2);

        PageResult<RecordBO> result = new PageResult<>();
        if (CollectionUtils.isNotEmpty(objectList)) {
            result.setCount(Long.valueOf(search2.size()));
            result.setTotalPage((search2.size() - 1) / params.getSize() + 1);
            result.setData(objectList.stream().map(recordConverter::convert).collect(Collectors.toList()));
        } else {
            result.setCount(0L);
            result.setTotalPage(0);
            result.setData(Lists.newArrayList());
        }
        result.setSuccess(true);
        result.setPageIndex(params.getPage());
        result.setPageSize(params.getSize());
        return result;
    }

    @Override
    public RepeaterResult<RecordDetailBO> getDetail(RecordParams params) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .timeout(new TimeValue(5, TimeUnit.SECONDS))
                .sort(new ScoreSortBuilder().order(SortOrder.DESC));
        if (StringUtils.isNotBlank(params.getAppName())) {
            sourceBuilder.query(QueryBuilders.termsQuery("appName", params.getAppName()));
        }
        if (StringUtils.isNotBlank(params.getTraceId())) {
            sourceBuilder.query(QueryBuilders.termsQuery("traceId", params.getTraceId()));
        }
        if (StringUtils.isNotBlank(params.getEnvironment())) {
            sourceBuilder.query(QueryBuilders.termsQuery("environment", params.getEnvironment()));
        }
        List<Map<String, Object>> search = esUtil.search(Constant.ES_INDEX, Constant.MODULE_INFO_ES_TYPE, sourceBuilder);
        List<Record> objectList = search.stream()
                .map(o -> BeanUtil.mapToBean(o, Record.class, true))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(objectList) || objectList.get(0) == null) {
            return ResultHelper.fail("data not exist");
        }
        return RepeaterResult.builder().success(true).data(recordDetailConverter.convert(objectList.get(0))).build();
    }

    @Override
    public RepeaterResult<RepeatModel> callback(String repeatId) {
        return null;
    }
}
