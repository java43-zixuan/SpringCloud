package com.example.userservice1.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.userservice1.entity.User;
import com.example.userservice1.service.IUserService;
import com.github.benmanes.caffeine.cache.Cache;
import io.swagger.annotations.Api;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@Api(tags = "本地缓存caffeine")
@RestController
@RequestMapping("/caffeine") //路由路径
public class CaffeineController {
    @Autowired
    Cache<String, Object> accessToken;
    @Autowired
    Cache<String, Object> refreshToken;
    @Autowired
    private IUserService userService;

    //@Cacheable 可以注解在某个类上，也可以注解在某个方法上，分别表示该类的所有方法都要使用缓存，该方法使用缓存。
    @Cacheable(cacheManager = "oneHourCacheManager", value = "human", key = "#{name}")
    @PostMapping("/test") //路由路径
    public User getPersonByName(String name){
        // 可以自定义代码，比如拿着 name 去数据库中查询此人的信息
        User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUserName,name));
        return user;
    }


    @GetMapping("/put")
    public void set(@RequestParam String code) throws ApiException {
        refreshToken.put("code",code);
    }

    @GetMapping("/get")
    public Object get(){
        Object code = refreshToken.asMap().get("code");
        return  code;
    }

}
