package com.example.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author ZH
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig{

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("SpringCloud搭建Demo API接口文档")
                        .description("# SpringCloud RESTful APIs")
                        .termsOfServiceUrl("http://www.xx.com/")
                        .contact(new Contact("xxx", "url", "xx@m.com"))
                        .version("1.0")
                        .build())
                //分组名称
                //.groupName("2.X版本")
                .select() // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.any())// 对所有api进行监控
                //不显示错误的接口地址
                .paths(PathSelectors.regex("(?!/error.*).*"))
                .build();
    }
}