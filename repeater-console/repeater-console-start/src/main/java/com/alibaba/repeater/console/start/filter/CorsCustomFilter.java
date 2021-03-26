package com.alibaba.repeater.console.start.filter;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @description: 重写cors跨域过滤器
 * @time: 2020/4/17
 */
public class CorsCustomFilter implements WebFilter {

    private final CorsConfigurationSource configSource;

    private final CorsProcessor processor;

    /**
     * Constructor accepting a {@link CorsConfigurationSource} used by the filter
     * to find the {@link CorsConfiguration} to use for each incoming request.
     *
     * @see UrlBasedCorsConfigurationSource
     */
    public CorsCustomFilter(CorsConfigurationSource configSource) {
        this(configSource, new DefaultCorsProcessor());
    }

    /**
     * Constructor accepting a {@link CorsConfigurationSource} used by the filter
     * to find the {@link CorsConfiguration} to use for each incoming request and a
     * custom {@link CorsProcessor} to use to apply the matched
     * {@link CorsConfiguration} for a request.
     *
     * @see UrlBasedCorsConfigurationSource
     */
    public CorsCustomFilter(CorsConfigurationSource configSource, CorsProcessor processor) {
        Assert.notNull(configSource, "CorsConfigurationSource must not be null");
        Assert.notNull(processor, "CorsProcessor must not be null");
        this.configSource = configSource;
        this.processor = processor;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (CorsUtils.isCorsRequest(request)) {
            CorsConfiguration corsConfiguration = this.configSource.getCorsConfiguration(exchange);
            if (corsConfiguration != null) {
                boolean isValid = this.processor.process(corsConfiguration, exchange);
                if (!isValid || CorsUtils.isPreFlightRequest(request)) {
                    return Mono.empty();
                }
            }
        }
        if (request.getMethod() == HttpMethod.OPTIONS) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.OK);
            return Mono.empty();
        }
        return chain.filter(exchange);
    }
}
