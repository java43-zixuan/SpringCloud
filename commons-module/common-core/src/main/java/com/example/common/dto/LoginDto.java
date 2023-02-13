package com.example.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value="登录接口入参")
@Data
public class LoginDto extends BaseDto{

    @ApiModelProperty(value = "用户名",required = true)
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "密码",required = true)
    @NotBlank(message = "密码不能为空")
    private String userPwd;

    @ApiModelProperty(value = "验证码", name = "verifyCode", example = "1234")
    private String verifyCode;

    @ApiModelProperty(value = "验证码ID", name = "verifyCodeId", example = "1234", required = true)
    @NotBlank(message = "验证码id不能为空")
    private String verifyCodeId;

    // 是否踢掉其他用户用
    @ApiModelProperty(value = "确认登陆标识", name = "confirmLoginFlg", example = "0", required = true)
    @NotBlank(message = "确认登陆标识不能为空")
    private String confirmLoginFlg;
}
