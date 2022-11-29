package com.example.userservice1.controller;

import com.example.common.dto.UserDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "路由测试模块")
@RestController
@RequestMapping("/demo") //路由路径
public class DemoController {
    @Value("${server.port}")
    private String serverPort;



    @PostMapping(value = "/hello")
    public String getServerPort(@RequestBody UserDto userDto){
        return "Hello! port: "+ serverPort;
    }

}
