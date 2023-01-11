package com.example.userservice3.controller;

import com.example.userservice3.entity.User;
import com.example.userservice3.service.IUserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "userService3的User接口")
@RestController
@RequestMapping("/test")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping(value = "/addUser")
    public void addUser(){
        User user = new User();
        user.setName("aaa");
        user.setPwd("qwd");
        userService.save(user);
        if(true){
            throw new RuntimeException("报错了！");
        }
    }

    @PostMapping(value = "/addUser2")
    public void addUser2(){
        User user = new User();
        user.setName("aaa");
        user.setPwd("qwd");
        userService.save(user);
    }
}
