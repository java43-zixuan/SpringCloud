package com.example.userservice2.test18;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 对接第三方平台或者前端字段不一致的情况下，后端使用别名处理
 * 属性别名：fastjson/jackjson
 * 一：post请求
 *      1、@JsonProperty:序列化和反序列化过程中修改java属性名
 * 二：get请求
 *      需要自定义注解进行处理 ex:@RequestParamAlias
 */
@Data
public class Bieming {

    @JsonProperty("user_name")//swagger中看到的入参字段为user_name,返回结果字段也是user_name
    private String userName;

    @JsonProperty("user_pwd")
    private String userPwd;

}
