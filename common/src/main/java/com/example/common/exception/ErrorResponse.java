package com.example.common.exception;


import com.example.common.vo.OpenError;
import com.example.common.vo.Response;

/**
 * @author TH
 */
public class ErrorResponse {


    /**
     * 错误返回封装类
     **/
    public static Response errorResponse(int responseCode, String errorCode, String errorMessage, String tips) {
        Response response = new Response();
        OpenError errors = new OpenError();
        errors.setCode(errorCode);
        errors.setMessage(errorMessage);
        errors.setTips(tips);
        response.setErrors(errors);
        response.setResponseCode(responseCode);
        return response;
    }
}
