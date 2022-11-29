package com.example.gatewaymodule.filter;


import com.example.gatewaymodule.config.AccessBindingHelper;
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
 * <pre>
 * access.binding.blacklist[0].url-pattern=/foo/bar
 * access.binding.blacklist[0].partner-code=abc
 * access.binding.blacklist[0].user=xyz
 * access.binding.blacklist[1].url-pattern=/foo/bar/*
 * access.binding.blacklist[1].partner-code=ab*c
 * access.binding.blacklist[2].url-pattern=/foo/*bar
 * access.binding.blacklist[2].user=*
 * access.binding.whitelist[0].url-pattern=/foo/bar
 * access.binding.whitelist[0].partner-code=aa,b*b,cc*
 * access.binding.whitelist[0].user=xx,yy
 *
 * @author web
 * </pre>
 * @author ld
 */
@Slf4j
@Component
public class AccessGlobalFilter implements GlobalFilter, Ordered {
    private static final String IGNORED_URL = "/v2/api-docs";

//    @Autowired
//    @Lazy
//    private AuthFeignService authFeignService;
    @Autowired
    private AccessBindingHelper accessBindingHelper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (accessBindingHelper.getSetting() == null) {
            return chain.filter(exchange);
        }
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        if (path.endsWith(IGNORED_URL)) {
            //swagger 多个项目切换放行处理
            return chain.filter(exchange);
        }

//        String token = AuthorizeGatewayFilterFactory.getToken(request);
//        AuthKeyVo vo = validateByAuth(token);
//        if (vo == null || vo.getAgencyCode() == null || vo.getAccountName() == null
//                || accessBindingHelper.canAccess(request.getPath().value(), vo.getAgencyCode(), vo.getAccountName())) {
//            ContextHandler.clear();
//            return chain.filter(exchange);
//        }
//        ContextHandler.clear();
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 14;
    }

//    private AuthKeyVo validateByAuth(String token) {
//        // 是否有缓存的结果
//        AuthKeyVo result = ContextHandler.getAuthKeyVo();
//        if (result != null) {
//            return result;
//        }
//        // token应该能够校验通过，才能获取租户和用户，进行判断
//        if (StringUtils.isNotBlank(token)) {
//            try {
//                OpenApiResponse<AuthKeyVo> resp = authFeignService.checkAuthKey(token);
//                OpenResponse<AuthKeyVo> openResponse = resp.getResponse();
//                return openResponse.getResult();
//            } catch (Exception e) {
//                log.info("用户authKey验证失败", e);
//            }
//        }
//        return null;
//    }
}
