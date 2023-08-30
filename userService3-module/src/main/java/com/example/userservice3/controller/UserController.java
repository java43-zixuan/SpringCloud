package com.example.userservice3.controller;

import com.example.common.exception.ServiceException;
import com.example.userservice3.entity.User;
import com.example.userservice3.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
        user.setCreateTime(LocalDateTime.now());
        userService.save(user);
        if(true){
            throw new RuntimeException("对不起，报错了！");
//            throw new ServiceException("对不起，报错了！");
        }
    }

    @PostMapping(value = "/addUser2")
    public void addUser2(){
        User user = new User();
        user.setName("aaa");
        user.setPwd("qwd");
        user.setCreateTime(LocalDateTime.now());
        userService.save(user);
    }
}
