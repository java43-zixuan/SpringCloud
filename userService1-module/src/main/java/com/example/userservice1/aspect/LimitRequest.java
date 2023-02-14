package com.example.userservice1.aspect;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitRequest {

    //毫秒，分钟，小时 之间的转换用算数
    // 限制时间 单位：毫秒
    long time() default 60000*60*24;
    // 允许请求的次数
    int count() default Integer.MAX_VALUE;
}
