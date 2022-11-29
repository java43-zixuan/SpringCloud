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
@ConfigurationProperties("access.binding")
public class AccessBindingProperties {
    private List<Entry> blacklist;
    private List<Entry> whitelist;
    private List<String> admin;

    @Getter
    @Setter
    public static class Entry {
        private String urlPattern;
        private String partnerCode;
        private String user;
    }
}
