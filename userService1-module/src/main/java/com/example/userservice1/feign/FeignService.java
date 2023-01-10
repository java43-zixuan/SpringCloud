package com.example.userservice1.feign;

import com.example.userservice1.entity.User;
import com.example.userservice1.service.IUserService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@GlobalTransactional(name = "seata-server", rollbackFor = Exception.class)
public class FeignService {

    @Autowired
    private UserService3Client userService3Client;

    @Autowired
    private IUserService userService;

    @Transactional(rollbackFor = Exception.class)
    public void addUser(User user) {
        userService3Client.insertData();
        userService.save(user);
        if(true){
            throw new RuntimeException("报错了！");
        }
    }
}
