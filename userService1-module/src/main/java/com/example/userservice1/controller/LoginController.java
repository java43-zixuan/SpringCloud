package com.example.userservice1.controller;

import com.example.common.dto.LoginDto;
import com.example.common.dto.VerifyCodeDto;
import com.example.common.vo.ResultVO;
import com.example.common.vo.VerifyCodeVo;
import com.example.userservice1.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Api(tags = "登录模块")
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @ApiOperation(value="登录")
    @PostMapping("/login")
    public ResultVO login(@RequestBody @Validated LoginDto loginDto){
        ResultVO result = new ResultVO();
        try {
            result = loginService.login(loginDto);
        } catch (Exception e) {
            result.setStatus(509);
            result.setMessage("登录失败！");
            log.error("===== businessHandle Exception =====", e);
        }
        return result;
    }
    @ApiOperation(value="登出")
    @PostMapping(value="/logout")
    public ResultVO logout(){
        return null;
    }


    @ApiOperation(value = "获取验证码")
    @PostMapping("/getVerifyCode")
    public ResultVO<VerifyCodeVo> getVerifyCode(@Validated @RequestBody VerifyCodeDto dto) {
        ResultVO<VerifyCodeVo> result= new ResultVO<>();
        try {
            result = loginService.getVerifyCode(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
