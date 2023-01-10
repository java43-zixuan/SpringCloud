package com.example.userservice3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = {"com.example.userservice3.mapper"})//指定要编译成接口实现类的包路径，在编译完成后这个包下的所有接口都会生成相应的接口实现类。
@ComponentScan(basePackages = {"com.example.*"})
public class UserService3Application {

    public static void main(String[] args) {
        SpringApplication.run(UserService3Application.class, args);
    }

}
