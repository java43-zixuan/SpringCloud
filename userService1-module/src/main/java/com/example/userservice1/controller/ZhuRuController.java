package com.example.userservice1.controller;

import com.example.userservice1.service.InterfaceTest;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 同一个接口有多个实现类，怎么确定使用哪个实现类
 */
@Api(tags = "注入测试")
@Slf4j
@RestController
@RequestMapping("/zhuru")
public class ZhuRuController {

    /**
     * @Autowired 和 @Qualifier 配合注入
     */
    @Qualifier("interfaceTestImpl1")
    @Autowired
    private InterfaceTest interfaceTest;


    @Resource(name = "interfaceTestImpl2")
    private InterfaceTest interfaceTest2;


    @GetMapping("/test")
    public void test(){
        interfaceTest.fun1();
        interfaceTest2.fun1();
    }


}
