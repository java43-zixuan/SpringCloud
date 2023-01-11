package com.example.userservice1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = {"com.example.userservice1.mapper"})//指定要编译成接口实现类的包路径，在编译完成后这个包下的所有接口都会生成相应的接口实现类。
@ComponentScan(basePackages = {"com.example.*"})//会自动扫描包路径下的@Controller、@Service、@Repository、@Component类，符合扫描规则的类会装配到spring容器中
@EnableFeignClients(basePackages = {"com.example.userservice1.feign"})// 开启Feign并扫描Feign客户端
public class UserService1Application {

    public static void main(String[] args) {
        SpringApplication.run(UserService1Application.class, args);
    }

}
