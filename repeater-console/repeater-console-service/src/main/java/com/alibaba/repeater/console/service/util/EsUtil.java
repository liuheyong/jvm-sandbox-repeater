package com.alibaba.repeater.console.service.util;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: liuheyng
 * @date: 2021/3/29 17:49
 * @description:
 */
@Slf4j
@Component
public class EsUtil {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public IndexResponse save(String indexName, String type, Object object, Object id) {
        try {
            IndexRequest request = new IndexRequest("post");
            request.index(indexName).type(type).id(String.valueOf(id)).source(BeanUtil.beanToMap(object));
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("插入数据成功");
            return response;
        } catch (Exception e) {
            log.error("插入数据失败", e);
        }
        return null;
    }

    public List<Map<String, Object>> search(String indexName, String type, SearchSourceBuilder searchSourceBuilder) {
        SearchRequest request = new SearchRequest();
        request.indices(indexName);
        request.types(type);
        request.source(searchSourceBuilder);
        SearchResponse response;
        List<Map<String, Object>> result = Lists.newArrayList();
        try {
            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            for (SearchHit hit : response.getHits()) {
                result.add(hit.getSourceAsMap());
            }
        } catch (IOException e) {
            log.error("查询失败", e);
        }
        return result;
    }

}
