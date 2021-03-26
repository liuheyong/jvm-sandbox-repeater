package com.alibaba.repeater.console.start.config;

import com.alibaba.repeater.console.start.filter.CorsCustomFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * @description: 跨域配置
 * @create: 2020-04-03 10:20
 **/
@Configuration
public class CorsFilterConfig {

    /**
     * 配置跨域
     *
     * @return
     */
    @Bean
    public CorsCustomFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // cookie跨域
        config.setAllowCredentials(Boolean.TRUE);
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        // 配置前端js允许访问的自定义响应头
        //config.addExposedHeader("setToken");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsCustomFilter(source);
    }

}