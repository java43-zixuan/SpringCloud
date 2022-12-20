package com.example.gatewaymodule.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

/**
 * application/json FilterFactory
 * 请求参数是否为json类型的过滤器
 *
 * @author ld
 */
@Component
public class JsonHeaderGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    private static final int ORDER = Ordered.HIGHEST_PRECEDENCE + 11;
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String IGNORED_URL = "/v2/api-docs";

    @Override
    public GatewayFilter apply(Object config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getPath().value();
            if (!path.endsWith(IGNORED_URL) && !APPLICATION_JSON.equalsIgnoreCase(request.getHeaders().getFirst(CONTENT_TYPE))) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                return response.setComplete();
            }
            return chain.filter(exchange);
        }, ORDER);
    }
}
