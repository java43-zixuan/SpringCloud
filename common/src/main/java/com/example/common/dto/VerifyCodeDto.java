package com.example.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VerifyCodeDto {
    @ApiModelProperty(value = "验证码id", name = "verifyCodeId", example = "1234", required = true)
    @NotNull(message = "验证码id不能为空")
    String verifyCodeId;// 浏览器唯一标识（类似sessionId）
}
