package com.example.userservice3.controller;

import com.example.common.dto.UserDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Api(tags = "userService3的测试接口")
@RestController
@RequestMapping("/demo") //路由路径
public class DemoController {
//    @Value("${server.port}")
//    private String serverPort;
//
//    @PostMapping(value = "/hello")
//    public String getServerPort(@RequestBody UserDto userDto){
//        return "Hello! port: "+ serverPort;
//    }
}
