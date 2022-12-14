package com.example.gatewaymodule.filter;


import com.example.gatewaymodule.util.Constants;
import com.example.gatewaymodule.util.GatewayLogUtil;
import com.example.gatewaymodule.util.RecorderServerHttpRequestDecorator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @author ZH
 */
@Component
@ConditionalOnProperty(value = "access.log.enable", matchIfMissing = true)
public class HigherRequestRecorderGlobalFilter implements GlobalFilter, Ordered {

    private static final String HTTP = "http";

    private static final String HTTPS = "https";

    private static final String WEBSOCKET = "websocket";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest originalRequest = exchange.getRequest();
        URI originalRequestUrl = originalRequest.getURI();

        //只记录http的请求
        String scheme = originalRequestUrl.getScheme();
        if ((!Constants.HTTP.equals(scheme) && !Constants.HTTPS.equals(scheme))) {
            return chain.filter(exchange);
        }

        String upgrade = originalRequest.getHeaders().getUpgrade();
        if (Constants.WEBSOCKET.equalsIgnoreCase(upgrade)) {
            return chain.filter(exchange);
        }

        //在 NettyRoutingFilter 之前执行， 基本上属于倒数第二个过滤器了
        //此时的request是 经过各种转换、转发之后的request
        //对应日志中的 代理请求 部分
        RecorderServerHttpRequestDecorator request = new RecorderServerHttpRequestDecorator(originalRequest);
        ServerWebExchange ex = exchange.mutate()
                .request(request)
                .build();

        return GatewayLogUtil.recorderRouteRequest(ex)
                .then(Mono.defer(() -> chain.filter(ex)));
    }

    @Override
    public int getOrder() {
        //在向业务服务转发前执行  NettyRoutingFilter 或 WebClientHttpRoutingFilter
        return Ordered.LOWEST_PRECEDENCE - 10;
    }
}
