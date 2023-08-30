package com.example.gatewaymodule.filter;


import com.example.gatewaymodule.config.AccessBindingHelper;
import com.example.gatewaymodule.util.ContextHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 访问权限控制Filter<br/>
 * 配置例：
 *
 * @author web
 * </pre>
 * @author ld
 */
@Slf4j
@Component
public class AccessGlobalFilter implements GlobalFilter, Ordered {
    private static final String IGNORED_URL = "/v2/api-docs";

    @Autowired
    private AccessBindingHelper accessBindingHelper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //直接放行
        return chain.filter(exchange);

//以下做了黑白名单的处理
//        if (accessBindingHelper.getSetting() == null) {
//            return chain.filter(exchange);
//        }
//        ServerHttpRequest request = exchange.getRequest();
//        String path = request.getPath().value();
//        if (path.endsWith(IGNORED_URL)) {
//            //swagger 多个项目切换放行处理
//            return chain.filter(exchange);
//        }
//
//        String token = AuthorizeGatewayFilterFactory.getToken(request);
//        AuthKeyVo vo = validateByAuth(token);
//        if (vo == null || vo.getAgencyCode() == null || vo.getAccountName() == null
//                || accessBindingHelper.canAccess(request.getPath().value(), vo.getAgencyCode(), vo.getAccountName())) {
//            ContextHandler.clear();
//            return chain.filter(exchange);
//        }
//        if(StringUtils.isNotBlank(token)){
//            return chain.filter(exchange);
//        }
//        ContextHandler.clear();
//
//
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(HttpStatus.UNAUTHORIZED);
//        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 14;
    }
}
