//package com.example.userservice1.feign.fallbackFactory;
//
//import com.example.userservice1.feign.UserService3Client;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
///**
// * @author
// * @date
// */
//@Slf4j
//@Component
//public class InvoiceApiFeignFallback implements UserService3Client {
//
//    @Setter
//    private Throwable cause;
//
//
//    @Override
//    public String insertData() {
//        log.error("feign 接口调用失败", cause);
//        return null;
//    }
//
//    @Override
//    public String insertData2() {
//        log.error("feign 接口调用失败", cause);
//        return null;
//    }
//}