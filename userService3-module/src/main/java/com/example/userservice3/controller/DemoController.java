package com.example.userservice3.controller;

import com.example.common.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
