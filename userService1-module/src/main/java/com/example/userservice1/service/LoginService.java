package com.example.userservice1.service;

import com.example.common.dto.LoginDto;
import com.example.common.dto.VerifyCodeDto;
import com.example.common.vo.ResultVO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface LoginService {

    /**
     * 登录
     */
    ResultVO login(LoginDto dto)throws JsonProcessingException;
    /**
     * 登出
     */
    ResultVO logout(String token);
    /**
     * 生成图片验证码
     * @param dto
     * @return
     */
    ResultVO getVerifyCode(VerifyCodeDto dto)throws Exception;
}
