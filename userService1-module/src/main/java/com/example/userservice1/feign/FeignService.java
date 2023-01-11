package com.example.userservice1.feign;

import com.example.userservice1.entity.User;
import com.example.userservice1.service.IUserService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class FeignService {

    @Autowired
    private UserService3Client userService3Client;

    @Autowired
    private IUserService userService;

    /**
     * 测试报错在被调用方
     * @param user
     */
    @GlobalTransactional(name = "seata-server", rollbackFor = Exception.class)
    public void addUser(User user) {
        userService.save(user);
        userService3Client.insertData();
    }

    /**
     * 测试报错在调用方
     * @param user
     */
    @GlobalTransactional(name = "seata-server", rollbackFor = Exception.class)
    public void addUser2(User user) {
        userService.save(user);
        userService3Client.insertData2();
        if(true){
            throw new RuntimeException("报错了！");
        }
    }
}
