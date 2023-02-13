package com.example.userservice1.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate工具类，主要用来提供RestTemplate对象
 */

@Configuration//加上这个注解作用，可以被Spring扫描
public class RestTemplateConfig {
    @Bean
    //@LoadBalanced //可以通过这个注解实现客户端负载均衡（ribbon） 调用服务名即可
    public RestTemplate restTemplate(){
        // RestTemplate restTemplate = new RestTemplate();
        // 设置中文乱码问题方式一
        // restTemplate.getMessageConverters().add(1,new StringHttpMessageConverter(Charset.forName("UTF-8")));
        // 设置中文乱码问题方式二
        // restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8)); // 支持中文编码
        return new RestTemplate();
    }
}
