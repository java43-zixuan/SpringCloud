package com.example.common.vo;



import com.alibaba.fastjson.annotation.JSONField;
import com.example.common.constant.UnitConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回数据，通过@JsonProperty兼容原先的返回字段名
 * @author ZH
 */
@Data
@ApiModel(value = "统一返回结果")
public class Response<T> implements Serializable {
    @ApiModelProperty("返回码")
    @JsonProperty("response_code")
    @JSONField(name = "response_code")
    private Integer responseCode;

    @ApiModelProperty("成功数据")
    public OpenResponse<T> response;

    @ApiModelProperty("失败数据")
    public OpenError errors;

    public static <T> Response<T> success(Integer code,String subCode,String message) {
        Response openApiResponse = new Response();
        openApiResponse.setResponseCode(UnitConstants.RESPONSE_SUCCESS_CODE);
        OpenResponse response = new OpenResponse<>();
        response.setCode(code);
        response.setSubCode(subCode);
        response.setMessage(message);
        openApiResponse.setResponse(response);
        return openApiResponse;
    }

    public static <T> Response<T> success(T t,Integer code,String subCode,String message) {
        Response<T>  openApiResponse = success( code, subCode, message);
        OpenResponse response = openApiResponse.getResponse();
        response.setResult(t);
        openApiResponse.setResponse(response);
        return openApiResponse;
    }

    public static Response fail(int responseCode, String errorCode, String errorMessage, String tips){
        Response response = new Response();
        OpenError errors = new OpenError();
        errors.setCode(errorCode);
        errors.setMessage(errorMessage);
        errors.setTips(tips);
        response.setErrors(errors);
        response.setResponseCode(responseCode);
        return response;
    }
    public static Response fail(int responseCode,ReturnCode returnCode ){
        Response response = new Response();
        OpenError errors = new OpenError();
        errors.setCode(returnCode.code());
        errors.setMessage(returnCode.message());
        errors.setTips(returnCode.tips());
        response.setErrors(errors);
        response.setResponseCode(responseCode);
        return response;
    }
    public static Response failOfInsufficientParameter(){
        Response response = new Response();
        OpenError errors = new OpenError();
        errors.setCode( UnitConstants.ERROR_CODE_4004);
        errors.setMessage(UnitConstants.ERROR_MESSAGE_4004);
        errors.setTips(UnitConstants.TIPS_4004);
        response.setErrors(errors);
        response.setResponseCode(UnitConstants.CODE_4004);
        return response;
    }
}
