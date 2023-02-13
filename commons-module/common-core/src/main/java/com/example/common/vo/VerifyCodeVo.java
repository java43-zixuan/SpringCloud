package com.example.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "获取验证码返回实体类")
@Data
public class VerifyCodeVo {

    @ApiModelProperty(value = "验证码地址",example = "http://121.36.0.50:81/fs/group1/M00/00/22/wKgMzGJzh3KALcGVAAAJzZoNreo619.jpg")
    private String fullPath;
}
