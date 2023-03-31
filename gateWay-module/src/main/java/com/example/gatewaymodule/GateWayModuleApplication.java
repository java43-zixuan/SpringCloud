package com.example.gatewaymodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//排除此类(DataSourceAutoConfiguration)的autoconfig
public class GateWayModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayModuleApplication.class, args);
    }

}
