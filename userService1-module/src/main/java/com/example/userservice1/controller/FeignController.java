package com.example.userservice1.controller;

import com.example.common.vo.ResultVO;
import com.example.userservice1.entity.User;
import com.example.userservice1.feign.FeignService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "feign调用")
@RestController
@RequestMapping("/feign") //路由路径
public class FeignController {

    @Autowired
    private FeignService feignService;

    @PostMapping(value = "/feignCall")
    public ResultVO feignCall(@RequestBody User user){
        try{
            feignService.addUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
        return ResultVO.success();
    }


    @PostMapping(value = "/feignCall2")
    public ResultVO feignCall2(@RequestBody User user){
        try{
            feignService.addUser2(user);
        }catch (Exception e){
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
        return ResultVO.success();
    }
}
