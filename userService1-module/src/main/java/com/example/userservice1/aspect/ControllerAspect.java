package com.example.userservice1.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import com.example.common.utils.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Controller请求切面，提供一下功能<br/>
 * 1、访问日志记录
 * @author ZH
 */
@Aspect
@Order(1)
@Slf4j
@Component
public class ControllerAspect {
    /**
     * 所有@RestController或@Controller注解标注  在com.bacs.platform.*.controller..包下的所有方法
     */
//    @Pointcut("(@target(org.springframework.web.bind.annotation.RestController) || @target(org.springframework.stereotype.Controller)) " +
//            "&& execution(public * com.example.userservice1.controller..*.*(..))")
    public void webAop() {
    }

//    @Around("webAop()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取 Request
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        // 记录下请求内容
        log.info("记录Web请求日志........");
        log.info("  Url：" + request.getMethod() + " " + request.getRequestURL());
        log.info("  Ip： " + ServletUtil.getClientIP(request));
        log.info("  Class-Method：" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        StringJoiner strJoiner = new StringJoiner(",","[","]");
        Arrays.stream(joinPoint.getArgs()).forEach(arg ->{
            if (arg instanceof ServletRequest) {
                strJoiner.add("ServletRequest");
            } else if (arg instanceof ServletResponse) {
                strJoiner.add("ServletResponse");
            } else {
                try {
                    strJoiner.add(JacksonUtil.toJson(arg));
                } catch (JsonProcessingException e) {
                    strJoiner.add(arg.toString());
                    log.error("web参数日志打印异常："+e.getMessage());
                }
            }
        });
        log.info("  Argus：" + strJoiner.toString());

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();

        log.info("Web请求耗时：" + (System.currentTimeMillis() - startTime) + "ms，返回：" + JacksonUtil.toJson(result));
        return result;

    }
}
