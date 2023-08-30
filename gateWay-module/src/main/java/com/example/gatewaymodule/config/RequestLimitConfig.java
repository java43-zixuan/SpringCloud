package com.example.gatewaymodule.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

/**
 * 自定义请求限制
 * @Author zx
 */
@Configuration
public class RequestLimitConfig {
    /**
     * 针对某一个接口ip 每一个ip 1s只能访问1次
     * @return
     */
    @Bean
    @Primary
    public KeyResolver ipKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getHeaders().getHost().getHostName());
    }
    /**
     * 针对路径限制 /doLogin
     * api就是接口 一般将gateway叫api网关
     * @return
     */
    @Bean
    public KeyResolver apiKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }
    /**
     * 针对userId限制
     * @return
     */
//    @Bean
//    public KeyResolver userKeyResolver() {
//        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getQueryParams().getFirst("userId")));
//    }


}
