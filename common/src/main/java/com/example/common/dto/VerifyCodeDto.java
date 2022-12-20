package com.example.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VerifyCodeDto {
    @ApiModelProperty(value = "验证码id", name = "verifyCodeId", example = "1234", required = true)
    @NotBlank(message = "验证码id不能为空")
    String verifyCodeId;// 浏览器唯一标识（类似sessionId）
}
