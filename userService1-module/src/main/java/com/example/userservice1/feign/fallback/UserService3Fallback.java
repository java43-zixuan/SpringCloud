package com.example.userservice1.feign.fallback;

import com.example.userservice1.feign.UserService3Client;
import org.springframework.stereotype.Component;

@Component
public class UserService3Fallback implements UserService3Client {

    @Override
    public String insertData() {
        return "现在服务器忙，请稍后再试！";
    }

}
