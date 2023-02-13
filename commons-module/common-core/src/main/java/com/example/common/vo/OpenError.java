package com.example.common.vo;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 失败数据，通过@JsonProperty兼容原先的返回字段名
 * @author ZH
 */
@Data
@ApiModel(value = "失败数据")
public class OpenError implements Serializable {
    @ApiModelProperty("失败码")
    @JsonProperty("error_code")
    @JSONField(name = "error_code")
    private String code;

    @ApiModelProperty("失败信息")
    @JsonProperty("error_message")
    @JSONField(name = "error_message")
    private String message;

    @ApiModelProperty("提示信息")
    private String tips;
}
