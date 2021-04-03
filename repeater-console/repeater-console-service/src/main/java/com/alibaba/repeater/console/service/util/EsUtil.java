package com.alibaba.repeater.console.service.util;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 插入es数据【单个Object写入】
     *
     * @Author: liuheyong
     * @date: 2021/3/29
     */
    public IndexResponse save(String indexName, String type, Object id, Object object) {
        try {
            log.info("插入es数据indexName:{}, type:{}, id:{}, object:{}", indexName, type, id, JSON.toJSONString(object));
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

    /**
     * 判断索引是否存在
     *
     * @Author: liuheyong
     * @date: 2021/3/29
     */
    public Boolean indexExists(String indexName) {
        try {
            log.info("判断是否存在索引:{}", indexName);
            GetIndexRequest getIndexRequest = new GetIndexRequest();
            getIndexRequest.indices(indexName);
            return restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询es数据
     *
     * @Author: liuheyong
     * @date: 2021/3/29
     */
    public List<Map<String, Object>> search(String indexName, String type, SearchSourceBuilder searchSourceBuilder) {
        SearchRequest request = new SearchRequest();
        request.indices(indexName);
        request.types(type);
        request.source(searchSourceBuilder);
        log.info("查询es数据 indexName：{}，type：{}，searchSourceBuilder：{}", indexName, type, searchSourceBuilder);
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
        log.info("查询数据成功,List<Map<String, Object>> result: {}", JSON.toJSONString(result));
        return result;
    }

}
