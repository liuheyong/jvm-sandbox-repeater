//package com.alibaba.repeater.console.start.controller.api;
//
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.SearchQuery;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author: liuheyng
// * @date: 2021/3/29 15:59
// * @description:
// */
//@Controller
//public class TestController {
//
//    @Resource
//    private ElasticsearchTemplate elasticSearchTemplate;
//    @Resource
//    private ElasticsearchRepository elasticsearchRepository;
//
//    @RequestMapping("/search")
//    @ResponseBody
//    public String findDoc() {
//        elasticsearchRepository.save(new ArrayList<>());
//        return "success";
//    }
//
//}
