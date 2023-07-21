//package com.example.common.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.redisson.spring.data.connection.RedissonConnectionFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.io.IOException;
//
//@Configuration
//public class RedissonConfig {
//
//    @Bean
//    public RedissonClient redisson() throws IOException {
//        Config config = new Config();
//        config.useSentinelServers().addSentinelAddress(
//                "redis://119.3.221.244:27001", "redis://119.3.221.244:27002", "redis://119.3.221.244:27003")
//                .setMasterName("mymaster")
//                .setCheckSentinelsList(false)
//                ;
//
//        return Redisson.create(config);
//    }
//
//
//}