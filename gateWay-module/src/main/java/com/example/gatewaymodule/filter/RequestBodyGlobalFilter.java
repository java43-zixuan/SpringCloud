package com.example.gatewaymodule.filter;

import com.example.gatewaymodule.util.DataBufferUtilFix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author ZH
 */
@Slf4j
@Component
public class RequestBodyGlobalFilter implements GlobalFilter, Ordered {
    private static final List<HttpMessageReader<?>> MESSAGE_READERS = HandlerStrategies.withDefaults().messageReaders();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        MediaType contentType = headers.getContentType();
        if (headers.getContentLength() > 0) {
            if (MediaType.APPLICATION_JSON.equals(contentType)) {
                return readBody(exchange, chain);
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 12;
    }

    private Mono<Void> readBody(ServerWebExchange exchange, GatewayFilterChain chain) {
        /* join the body */
        return DataBufferUtilFix.join(exchange.getRequest().getBody()).flatMap(dataBufferWrapper -> {
            byte[] bytes = dataBufferWrapper.getData();
            Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                DataBufferUtils.retain(buffer);
                return Mono.just(buffer);
            });
            /* repackage ServerHttpRequest */
            ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return cachedFlux;
                }
            };
            /* mutate exchange with new ServerHttpRequest */
            ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
            /* read body string with default messageReaders */
            return ServerRequest.create(mutatedExchange, MESSAGE_READERS).bodyToMono(String.class)
                    .doOnNext(objectValue -> {
                        log.debug("Read JsonBody:{}", objectValue);
                    }).then(chain.filter(mutatedExchange));
        });
    }
}
