package com.example.common.vo;


import com.example.common.constant.UnitConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 内部微服务返回对象
 * @author ZH
 */
@Data
@ApiModel(value = "内部微服务统一返回结果")
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    @ApiModelProperty("状态码")
    private Integer status;

    /**
     * 状态信息
     */
    @ApiModelProperty("状态信息")
    private String message;

    /**
     * data字段
     **/
    @ApiModelProperty("结果对象")
    private T data;

    public ResultVO() {
        status = UnitConstants.SUCCESS_CODE;
        message = "SUCCESS";
        data = null;
    }

    /**
     * 返回成功，无结果对象
     * @param <T> 无
     * @return ResultVO
     */
    public static<T> ResultVO<T> success() {
        return new ResultVO<>();
    }

    /**
     * 返回成功，有结果对象
     * @param t 结果对象
     * @param <T> 结果对象
     * @return ResultVO
     */
    public static <T> ResultVO<T> success(T t) {
        ResultVO<T> response = new ResultVO<>();
        response.setData(t);
        return response;
    }

    /**
     * 返回失败，使用500和FAIL
     * @param <T> 无
     * @return ResultVO
     */
    public static <T> ResultVO<T> fail(){
        ResultVO<T> response = new ResultVO<>();
        response.setStatus(UnitConstants.FAIL_CODE);
        response.setMessage("FAIL");
        return response;
    }

    /**
     * 返回失败，有失败信息
     * @param message  失败描述
     * @return ResultVO
     */
    public static <T> ResultVO<T> fail(String message) {
        ResultVO<T> response = new ResultVO<>();
        response.setStatus(UnitConstants.FAIL_CODE);
        response.setMessage(message);
        return response;
    }

    /**
     * 返回失败，有失败码和失败信息
     * @param resultCode  失败码
     * @param message      失败描述
     * @return ResultVO
     */
    public static <T> ResultVO<T> fail(int resultCode, String message) {
        ResultVO<T> response = new ResultVO<>();
        response.setStatus(resultCode);
        response.setMessage(message);
        return response;
    }


    /**
     * 返回失败，有失败码、失败信息和失败对象
     * @param resultCode  失败码
     * @param message      失败描述
     * @param <T/>             失败对象
     * @return ResultVO
     */
    public static <T> ResultVO<T> fail(int resultCode, String message , T t) {
        ResultVO<T> response = new ResultVO<>();
        response.setStatus(resultCode);
        response.setMessage(message);
        response.setData(t);
        return response;
    }
}
