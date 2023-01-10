package com.example.userservice1.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "userService3-module")
public interface UserService3Client {

    @PostMapping("/test/addUser")
    String insertData();

}
