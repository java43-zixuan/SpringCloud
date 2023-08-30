package com.example.userservice2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;


@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = {"com.example.userservice2.mapper"})
@ComponentScan(basePackages = {"com.example.*"})
public class UserService2Application {

    public static void main(String[] args) {
        SpringApplication.run(UserService2Application.class, args);
        System.out.println("============= UserService2启动成功=============");
    }

}
