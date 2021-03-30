package com.alibaba.repeater.console.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.Constants;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializeException;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.HttpUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.core.wrapper.SerializerWrapper;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.constant.Constant;
import com.alibaba.repeater.console.common.domain.ModuleConfigBO;
import com.alibaba.repeater.console.common.domain.ModuleInfoBO;
import com.alibaba.repeater.console.common.domain.PageResult;
import com.alibaba.repeater.console.common.params.ModuleConfigParams;
import com.alibaba.repeater.console.common.params.ModuleInfoParams;
import com.alibaba.repeater.console.dal.dao.ModuleConfigDao;
import com.alibaba.repeater.console.dal.model.ModuleConfig;
import com.alibaba.repeater.console.service.ModuleConfigService;
import com.alibaba.repeater.console.service.ModuleInfoService;
import com.alibaba.repeater.console.service.convert.ModuleConfigConverter;
import com.alibaba.repeater.console.service.util.EsUtil;
import com.alibaba.repeater.console.service.util.JacksonUtil;
import com.alibaba.repeater.console.service.util.ResultHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * {@link }
 * <p>
 *
 * @author zhaoyb1990
 */
@Service("moduleConfigService")
public class ModuleConfigServiceImpl implements ModuleConfigService {

    @Resource
    private EsUtil esUtil;
    @Resource
    private ModuleConfigDao moduleConfigDao;
    @Resource
    private ModuleConfigConverter moduleConfigConverter;
    @Resource
    private ModuleInfoService moduleInfoService;
    @Value("${repeat.config.url}")
    private String configURL;

    @Override
    public PageResult<ModuleConfigBO> list(ModuleConfigParams params) {
        if (!esUtil.indexExists(Constant.ES_INDEX)) {
            PageResult<ModuleConfigBO> pageResult = new PageResult<>();
            pageResult.setSuccess(false);
            pageResult.setMessage("no such index: " + Constant.ES_INDEX);
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
        if (StringUtils.isNotBlank(params.getEnvironment())) {
            sourceBuilder.query(QueryBuilders.termsQuery("environment", params.getEnvironment()));
        }
        List<Map<String, Object>> search = esUtil.search(Constant.ES_INDEX, Constant.MODULE_CONFIG_ES_TYPE, sourceBuilder);
        List<ModuleConfig> objectList = search.stream()
                .map(o -> BeanUtil.mapToBean(o, ModuleConfig.class, true))
                .collect(Collectors.toList());

        SearchSourceBuilder sourceBuilder2 = new SearchSourceBuilder()
                .timeout(new TimeValue(5, TimeUnit.SECONDS))
                .sort(new ScoreSortBuilder().order(SortOrder.DESC));
        List<Map<String, Object>> search2 = esUtil.search(Constant.ES_INDEX, Constant.MODULE_CONFIG_ES_TYPE, sourceBuilder2);

        PageResult<ModuleConfigBO> result = new PageResult<>();
        if (CollectionUtils.isNotEmpty(objectList)) {
            result.setCount(Long.valueOf(search2.size()));
            result.setTotalPage((search2.size() - 1) / params.getSize() + 1);
            result.setData(objectList.stream().map(moduleConfigConverter::convert).collect(Collectors.toList()));
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
    public RepeaterResult<ModuleConfigBO> query(ModuleConfigParams params) {
        if (!esUtil.indexExists(Constant.ES_INDEX)) {
            return ResultHelper.fail("no such index: " + Constant.ES_INDEX);
        }
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .timeout(new TimeValue(5, TimeUnit.SECONDS))
                .query(QueryBuilders.termsQuery("appName", params.getAppName()))
                .query(QueryBuilders.termsQuery("environment", params.getEnvironment()))
                .sort(new ScoreSortBuilder().order(SortOrder.DESC));
        List<Map<String, Object>> search = esUtil.search(Constant.ES_INDEX, Constant.MODULE_CONFIG_ES_TYPE, sourceBuilder);
        List<ModuleConfig> objectList = search.stream()
                .map(o -> BeanUtil.mapToBean(o, ModuleConfig.class, true))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(objectList)) {
            return ResultHelper.fail("data not exist");
        }
        return ResultHelper.success(moduleConfigConverter.convert(objectList.get(0)));
    }

    @Override
    public RepeaterResult<ModuleConfigBO> saveOrUpdate(ModuleConfigParams params) {
        ModuleConfig moduleConfig = null;
        RepeaterResult<ModuleConfigBO> result = this.query(params);
        if (result.isSuccess() && result.getData() != null) {
            moduleConfig = new ModuleConfig();
            BeanUtils.copyProperties(result.getData(), moduleConfig);
        }
        if (moduleConfig != null) {
            moduleConfig.setConfig(params.getConfig());
        } else {
            moduleConfig = new ModuleConfig();
            moduleConfig.setAppName(params.getAppName());
            moduleConfig.setEnvironment(params.getEnvironment());
            moduleConfig.setConfig(params.getConfig());
            moduleConfig.setGmtCreate(new Date());
        }
        moduleConfig.setGmtModified(new Date());
        esUtil.save(Constant.ES_INDEX, Constant.MODULE_CONFIG_ES_TYPE, moduleConfig.getGmtModified(), moduleConfig);
        return ResultHelper.success(moduleConfigConverter.convert(moduleConfig));
    }

    @Override
    public RepeaterResult<ModuleConfigBO> push(ModuleConfigParams params) {
        ModuleConfig moduleConfig;
        RepeaterResult<ModuleConfigBO> configResult = this.query(params);
        if (configResult.isSuccess() && configResult.getData() != null) {
            moduleConfig = new ModuleConfig();
            BeanUtils.copyProperties(configResult.getData(), moduleConfig);
        } else {
            return ResultHelper.fail("config not exist");
        }
        ModuleInfoParams moduleInfoParams = new ModuleInfoParams();
        moduleInfoParams.setAppName(params.getAppName());
        moduleInfoParams.setEnvironment(params.getEnvironment());
        // a temporary size set
        moduleInfoParams.setSize(1000);
        PageResult<ModuleInfoBO> result = moduleInfoService.query(moduleInfoParams);
        if (result.getCount() == 0) {
            return ResultHelper.fail("no alive module, don't need to push config.");
        }
        String data;
        try {
            RepeaterConfig config = JacksonUtil.deserialize(moduleConfig.getConfig(), RepeaterConfig.class);
            data = SerializerWrapper.hessianSerialize(config);
        } catch (SerializeException e) {
            return ResultHelper.fail("serialize config occurred error, message = " + e.getMessage());
        }
        final Map<String, String> paramMap = new HashMap<>(2);
        paramMap.put(Constants.DATA_TRANSPORT_IDENTIFY, URLEncoder.encode(data));
        final Map<String, HttpUtil.Resp> respMap = Maps.newHashMap();
        result.getData().forEach(module -> {
            HttpUtil.Resp resp = HttpUtil.doGet(String.format(configURL, module.getIp(), module.getPort()), paramMap);
            respMap.put(module.getIp(), resp);
        });
        String ips = respMap.entrySet().stream().filter(entry -> !entry.getValue().isSuccess()).map(Map.Entry::getKey).collect(Collectors.joining(","));
        if (StringUtils.isNotEmpty(ips)) {
            return ResultHelper.success(ips + " push failed.");
        }
        return ResultHelper.success();
    }
}
