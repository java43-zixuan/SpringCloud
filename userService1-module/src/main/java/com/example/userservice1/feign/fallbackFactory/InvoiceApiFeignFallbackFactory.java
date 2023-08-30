//package com.example.userservice1.feign.fallbackFactory;
//
//import com.example.userservice1.feign.UserService3Client;
//import feign.hystrix.FallbackFactory;
//import org.springframework.stereotype.Component;
//
//@Component
//public class InvoiceApiFeignFallbackFactory implements FallbackFactory<UserService3Client> {
//    @Override
//    public UserService3Client create(Throwable throwable) {
//        InvoiceApiFeignFallback invoiceApiFeignFallback = new InvoiceApiFeignFallback();
//        invoiceApiFeignFallback.setCause(throwable);
//        return invoiceApiFeignFallback;
//    }
//}
