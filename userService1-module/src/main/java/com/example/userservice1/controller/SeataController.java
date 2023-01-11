package com.example.userservice1.controller;

import com.example.userservice1.entity.User;
import com.example.userservice1.feign.FeignService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "seata分布式事务")
@RestController
@RequestMapping("/seata") //路由路径
public class SeataController {

    @Autowired
    private FeignService feignService;

    @PostMapping(value = "/test")
    public void seataTest(@RequestBody User user){
        feignService.addUser(user);
    }

    @PostMapping(value = "/test2")
    public void seataTest2(@RequestBody User user){
        feignService.addUser2(user);
    }

}
