//package com.example.userservice1.RabbitMq.SimpleMode;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class ProducerTest {
//    @Autowired
//    RabbitTemplate rabbitTemplate;
//    public void simpleProduct(){
//        for (int num = 0; num < 20; num++) {
//            rabbitTemplate.convertAndSend("simpleQueue", "简单模式"+num);
//        }
//    }
//}
