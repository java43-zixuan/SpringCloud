package com.example.userservice1.controller;

import com.example.common.dto.UserDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "kafka测试模块")
@RestController
@RequestMapping("/kafka") //路由路径
public class KafkaTestController {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @PostMapping(value = "/setKafka")
    public String setKafka(@RequestBody UserDto userDto){
        String topic = "test-topic";
        String key = "ceshi";
        String value = "kafka测试数据";
//        kafkaTemplate.send(topic, key, JSON.toJSONString(value).getBytes());
//        kafkaTemplate.send(topic, key, JSON.toJSONString(value).getBytes());
//        kafkaTemplate.send(topic, key, JSON.toJSONString(value).getBytes());
//        kafkaTemplate.send(topic, key, JSON.toJSONString(value).getBytes());
//        kafkaTemplate.send(topic, key, JSON.toJSONString(value).getBytes());
//        kafkaTemplate.send(topic, key, JSON.toJSONString(value).getBytes());
//        kafkaTemplate.send(topic, key, JSON.toJSONString(value).getBytes());
//        kafkaTemplate.send(topic, key, JSON.toJSONString(value).getBytes());
//        kafkaTemplate.send(topic, key, JSON.toJSONString(value).getBytes());

        return "kafka send ok!";
    }

    @PostMapping(value = "/getKafka")
    public String getKafka(@RequestBody UserDto userDto){

        return "kafka get ok!";
    }


}
