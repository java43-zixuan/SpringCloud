package com.example.userservice2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = {"com.example.userservice2.mapper"})//指定要编译成接口实现类的包路径，在编译完成后这个包下的所有接口都会生成相应的接口实现类。
public class UserService2Application {

    public static void main(String[] args) {
        SpringApplication.run(UserService2Application.class, args);
    }

}
