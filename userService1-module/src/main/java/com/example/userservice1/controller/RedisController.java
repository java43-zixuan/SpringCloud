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
import java.util.Set;

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

    @ApiOperation(value="redisTemplate测试-opsForHash")
    @PostMapping("/opsForHash")
    public void opsForHash(){
        try {
            /**
             * redis 存取hash数据
             */
            redisTemplate.opsForHash().put("user:103", "name", "李易峰");
            redisTemplate.opsForHash().put("user:103", "age", "21");
            Map<Object, Object> entries = redisTemplate.opsForHash().entries("user:103");
            System.out.println("map数据：" + entries);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @ApiOperation(value="redisTemplate测试-opsForValue")
    @PostMapping("/opsForValue")
    public void opsForValue(){
        try {
            /**
             * redis 存取String数据
             */
            redisTemplate.opsForValue().set("赵轩", "31");
            String str = redisTemplate.opsForValue().get("赵轩").toString();
            System.out.println("String数据：" + str);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @ApiOperation(value="redisTemplate测试-opsForList")
    @PostMapping("/opsForList")
    public void opsForList(){
        try {
            /**
             * redis 存取List数据
             */
            redisTemplate.opsForList().leftPush("list", "鲁阳");
            redisTemplate.opsForList().leftPush("list", "鲁馨孺");
            redisTemplate.opsForList().leftPush("list","赵轩");
            Long size = redisTemplate.opsForList().size("list");
            System.out.println("List数据大小：" + size);
            String ListStr = redisTemplate.opsForList().index("list", 1).toString();
            System.out.println("List第1条数据：" + ListStr);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @ApiOperation(value="redisTemplate测试-opsForSet")
    @PostMapping("/opsForSet")
    public void opsForSet(){
        try {
            /**
             * redis 存取Set数据
             */
            redisTemplate.opsForSet().add("set", "鲁阳");
            redisTemplate.opsForSet().add("set", "鲁馨孺");
            redisTemplate.opsForSet().add("set", "赵轩");
            redisTemplate.opsForSet().add("set", "赵轩");
            redisTemplate.opsForSet().add("set", "鲁馨孺");
            Long setSize = redisTemplate.opsForSet().size("set");
            System.out.println("Set数据大小:" + setSize);
            Set set = redisTemplate.opsForSet().members("set");
            System.out.println("Set数据:" + set);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ApiOperation(value="redisTemplate测试-opsForZSet")
    @PostMapping("/opsForZSet")
    public void opsForZSet(){
        try {
            /**
             * redis 存取zSet数据
             */
            redisTemplate.opsForZSet().add("zSet", "鲁阳", 0);
            redisTemplate.opsForZSet().add("zSet", "鲁馨孺", 2);
            redisTemplate.opsForZSet().add("zSet", "赵轩", 1);
            Long zSetSize = redisTemplate.opsForZSet().size("zSet");
            System.out.println("zSet数据大小:" + zSetSize);
            Set zSet = redisTemplate.opsForZSet().range("zSet", 1, 2);
            System.out.println("zSet第1，2条数据：" + zSet);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
