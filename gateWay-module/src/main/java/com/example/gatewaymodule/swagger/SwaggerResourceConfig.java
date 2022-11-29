package com.example.gatewaymodule.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZH
 */
@Component
public class SwaggerResourceConfig implements SwaggerResourcesProvider {
    private final RouteLocator routeLocator = null;
    private final GatewayProperties gatewayProperties;

    @Value("${swagger-agg.generate-routes:}")
    private List<String> aggRouteIds;

    public SwaggerResourceConfig(GatewayProperties gatewayProperties) {
        this.gatewayProperties = gatewayProperties;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
//         List<String> aggRouteIds = new ArrayList<>();
//         routeLocator.getRoutes().subscribe(route -> aggRouteIds.add(route.getId()));
        gatewayProperties.getRoutes().stream().filter(routeDefinition -> aggRouteIds.contains(routeDefinition.getId())).forEach(route -> {
            route.getPredicates().stream()
                    .filter(predicateDefinition -> "Path".equals(predicateDefinition.getName()))
                    .forEach(predicateDefinition -> resources.add(swaggerResource(route.getId(),
                            predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
                                    .replace("**", "v2/api-docs"))));
        });
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource r = new SwaggerResource();
        r.setName(name);
        r.setLocation(location);
        r.setSwaggerVersion("2.0");
        return r;
    }
}
