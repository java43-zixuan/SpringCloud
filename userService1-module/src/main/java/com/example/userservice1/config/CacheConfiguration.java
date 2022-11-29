package com.example.userservice1.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching  //注解用于开启 springboot 的缓存功能，可以放在此处，也可以放在 application 启动类的头上。
public class CacheConfiguration {
    @Bean(name = "oneHourCacheManager")
    public CacheManager oneHourCacheManager(){
        Caffeine caffeine = Caffeine.newBuilder()
                .initialCapacity(10) //初始大小
                .maximumSize(11)  //最大大小
                .expireAfterWrite(1, TimeUnit.HOURS); //写入/更新之后1小时过期

        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setAllowNullValues(true);
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }


    long accessToken = 1;
    @Bean
    public Cache<String, Object> accessToken() {
        return Caffeine.newBuilder()
                // 设置最后一次写入后经过固定时间过期
                .expireAfterWrite(accessToken, TimeUnit.MINUTES)
                // 初始的缓存空间大小
                .initialCapacity(15000)
                // 缓存的最大条数
                .maximumSize(15000)
                .build();
    }

    long refreshToken = 2;
    @Bean
    public Cache<String, Object> refreshToken() {
        return Caffeine.newBuilder()
                // 设置最后一次写入后经过固定时间过期
                .expireAfterWrite(refreshToken, TimeUnit.MINUTES)
                // 初始的缓存空间大小
                .initialCapacity(15000)
                // 缓存的最大条数
                .maximumSize(15000)
                .build();

    }
}

