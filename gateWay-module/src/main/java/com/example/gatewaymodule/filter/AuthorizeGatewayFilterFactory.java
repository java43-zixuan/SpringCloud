//package com.example.gatewaymodule.filter;
//
//import com.example.gatewaymodule.config.AuthorizationProperties;
//import com.example.gatewaymodule.util.ContextHandler;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpCookie;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import reactor.core.publisher.Flux;
//
//import javax.annotation.PostConstruct;
//import java.nio.CharBuffer;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.atomic.AtomicReference;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
//
///**
// * 权限认证FilterFactory
// *
// * @author ld
// */
//@Slf4j
//@Component
//public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {
//    private static final int ORDER = Ordered.HIGHEST_PRECEDENCE + 13;
//    private static final String TOKEN_PARAM_KEY = "authkey";
//
////    @Autowired
////    @Lazy
////    private AuthFeignService authFeignService;
//    @Autowired
//    private AuthorizationProperties exclusion;
//    private List<Pattern> urlPatterns = new ArrayList<>();
//
//    @PostConstruct
//    public void init() {
//        if (exclusion != null) {
//            urlPatterns = Optional.ofNullable(exclusion.getIgnoredUrls()).orElse(new ArrayList<>()).stream()
//                    .map(AuthorizeGatewayFilterFactory::toPattern).collect(Collectors.toList());
//        }
//    }
//
//    @Override
//    public Class<Config> getConfigClass() {
//        return Config.class;
//    }
//
//    @Override
//    public List<String> shortcutFieldOrder() {
//        return Collections.singletonList("enabled");
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return new OrderedGatewayFilter((exchange, chain) -> {
//            if (!config.isEnabled()) {
//                return chain.filter(exchange);
//            }
//            ServerHttpRequest request = exchange.getRequest();
//            if (isIgnoredUrl(request)) {
//                return chain.filter(exchange);
//            }
//
//            String token = getToken(request);
//            if (StringUtils.isEmpty(token) || isTokenInvalidByAuth(token)) {
//                ServerHttpResponse response = exchange.getResponse();
//                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                return response.setComplete();
//            }
//
//            return chain.filter(exchange);
//        }, ORDER);
//    }
//
//    /**
//     * @return token校验不通过时返回true
//     */
//    private boolean isTokenInvalidByAuth(String token) {
////        try {
////            OpenApiResponse<AuthKeyVo> resp = authFeignService.checkAuthKey(token);
////            OpenResponse<AuthKeyVo> openResponse = resp.getResponse();
////            boolean result = !RESPONSE_SUCCESS_CODE.equals(resp.getResponseCode()) || !SUCCESS_CODE.equals(openResponse.getCode());
////            if (!result) {
////                // 通过
////                ContextHandler.setAuthKeyVo(openResponse.getResult());
////            }
////            return result;
////        } catch (Exception e) {
////            log.info("用户authKey验证失败", e);
////            return true;
////        }
//        return false; //恢复后应去掉
//    }
//
//    private boolean isIgnoredUrl(ServerHttpRequest request) {
//        String path = request.getPath().value();
//        for (Pattern p : urlPatterns) {
//            if (p.matcher(path).matches()) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public static String getToken(ServerHttpRequest request) {
//        // 优先从Header或cookie中获取Authorization
//        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        if (StringUtils.isEmpty(token)) {
//            boolean found = false;
//            for (List<HttpCookie> cookies : request.getCookies().values()) {
//                for (HttpCookie cookie : cookies) {
//                    if (cookie.getName().equals(HttpHeaders.AUTHORIZATION)) {
//                        token = cookie.getValue();
//                        found = true;
//                        break;
//                    }
//                }
//                if (found) {
//                    break;
//                }
//            }
//        }
//        // 兼容从requestBody获取token
////        if (StringUtils.isEmpty(token)) {
////            AtomicReference<String> requestBody = new AtomicReference<>("");
////            RecorderServerHttpRequestDecorator requestDecorator = new RecorderServerHttpRequestDecorator(request);
////            Flux<DataBuffer> body = requestDecorator.getBody();
////            body.subscribe(buffer -> {
////                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
////                requestBody.set(charBuffer.toString());
////            });
////            JSONObject requestParams = JSONObject.parseObject(requestBody.get());
////            token = requestParams.getString(TOKEN_PARAM_KEY);
////        }
//        return token;
//    }
//
//    private static Pattern toPattern(String s) {
//        return Pattern.compile("^" + s.replaceAll("\\*", ".*") + "$");
//    }
//
//    public static class Config {
//        // 控制是否开启认证
//        private boolean enabled;
//
//        public Config() {
//        }
//
//        public boolean isEnabled() {
//            return enabled;
//        }
//
//        public void setEnabled(boolean enabled) {
//            this.enabled = enabled;
//        }
//    }
//}
