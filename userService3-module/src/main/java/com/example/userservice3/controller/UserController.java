package com.example.userservice3.controller;

import com.example.userservice3.entity.User;
import com.example.userservice3.service.IUserService;
import io.swagger.annotations.Api;
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
    @Transactional
    public void addUser(){
        User user = new User();
        user.setName("aaa");
        user.setPwd("qwd");
        userService.save(user);
    }
}
