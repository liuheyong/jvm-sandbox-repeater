package com.alibaba.repeater.console.start.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ElasticsearchConfig {
    @Value("${spring.elasticsearch.host}")
    public String host;
    @Value("${spring.elasticsearch.httpPort}")
    public int httpPort;
    @Value("${spring.elasticsearch.rest.username:}")
    public String username;
    @Value("${spring.elasticsearch.rest.password:}")
    public String password;
    @Value("${spring.elasticsearch.scheme:http}")
    public String scheme;

    @Bean(name = "restHighLevelClient", destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() {
        boolean auth = StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password);
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(host, httpPort, scheme));
        if (auth) {
            restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            });
        }
        return new RestHighLevelClient(restClientBuilder);
    }
}
