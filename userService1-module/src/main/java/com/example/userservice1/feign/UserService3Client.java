package com.example.userservice1.feign;

import com.example.userservice1.feign.fallback.UserService3Fallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "userService3-module", fallback = UserService3Fallback.class)
public interface UserService3Client {

    @PostMapping("/test/addUser")
    String insertData();

}
