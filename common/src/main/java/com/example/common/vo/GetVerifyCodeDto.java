package com.example.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetVerifyCodeDto {

        @ApiModelProperty(value = "验证码地址",example = "http://121.36.0.50:81/fs/group1/M00/00/22/wKgMzGJzh3KALcGVAAAJzZoNreo619.jpg")
        private String fullPath;
}
