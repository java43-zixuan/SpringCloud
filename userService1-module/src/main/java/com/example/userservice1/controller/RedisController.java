package com.example.userservice1.controller;

import com.example.common.dto.LoginDto;
import com.example.common.vo.ResultVO;
import com.example.userservice1.service.IRedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@Api(tags = "redis验证")
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private IRedisService redisService;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String keyStr = "test";

    @ApiOperation(value="插入数据")
    @PostMapping("/save")
    public ResultVO save(@RequestBody LoginDto loginDto){
        String errorLockAccountKey =keyStr + loginDto.getUserName();
        try {
            redisService.saveObjectToJson(errorLockAccountKey, loginDto.getUserPwd(), 3600000);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResultVO.success();
    }

    @ApiOperation(value="查询数据")
    @PostMapping("/query")
    public ResultVO query(@RequestBody LoginDto loginDto){
        String errorLockAccountKey =keyStr + loginDto.getUserName();
        String errorLockAccount= redisService.getKey(errorLockAccountKey);
        return ResultVO.success(errorLockAccount);
    }


    public void redisTest(){
        /**
         * redis 存取hash数据
         */
        redisTemplate.opsForHash().put("user:103","name","李易峰");
        redisTemplate.opsForHash().put("user:103","age","21");
        Map<Object, Object> entries = redisTemplate.opsForHash().entries("user:103");
        System.out.println(entries);
        /**
         * redis 存取String数据
         */
        redisTemplate.opsForValue().set("赵轩","31");
        String str = redisTemplate.opsForValue().get("赵轩").toString();
        System.out.println(str);

        /**
         * redis 存取List数据
         */
//        redisTemplate.opsForList().set("");
//        String str = redisTemplate.opsForValue().get("赵轩").toString();
//        System.out.println(str);

    }
}
