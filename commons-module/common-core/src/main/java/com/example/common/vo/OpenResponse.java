package com.example.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 成功数据，通过@JsonProperty兼容原先的返回字段名
 * @param <T>
 * @author ZH
 */
@Data
@ApiModel(value = "成功数据")
public class OpenResponse<T> implements Serializable {

    @ApiModelProperty("返回码")
    private int code;

    @ApiModelProperty("子返回码")
    @JSONField(name = "subcode")
    @JsonProperty("subcode")
    private String subCode;

    @ApiModelProperty("返回信息")
    private String message;

    @ApiModelProperty("返回对象")
    private T result;

}
