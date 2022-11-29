package com.example.gatewaymodule.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ld
 */
@Getter
@Setter
@RefreshScope
@Component
@ConfigurationProperties("authorization")
public class AuthorizationProperties {
    private List<String> ignoredUrls;
}
