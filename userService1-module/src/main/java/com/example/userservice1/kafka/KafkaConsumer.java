package com.example.userservice1.kafka;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component //@component是spring中的一个注解，它的作用就是实现bean的注入。@Component泛指各种组件，就是说当我们的类不属于各种归类的时候（不属于@Controller、@Services等的时候），我们就可以使用@Component来标注这个类。
@RequiredArgsConstructor  //@RequiredArgsConstructor是lombok提供的一个注解，用于依赖注入 出现在代码中相当于：@Autowired
public class KafkaConsumer {
    /**
     * 共享单车消费Listener, 批量处理加上containerFactory = "batchFactory"
     * @param records 消息记录对象，此处为批量消费，若单条消费，此处改为ConsumerRecord<String, String>
     * @param consumer 消费者对象，可以获取分区、主题等信息，也可进行手动提交操作
     */
    //@KafkaListener(id = "layer_test_consumer",topics = {"test-topic"},groupId ="first2", containerFactory = "batchFactory")
    public void listen(ConsumerRecords<String, String> records, Consumer<String, String> consumer) {
        if (records.isEmpty()) {
            return;
        }
        // 消息逻辑处理
        for (ConsumerRecord<String, String> record : records) {
            System.out.println(record.key());
            System.out.println(record.offset());
            System.out.println(record.value());
        }
        consumer.commitSync();//提交offset

    }
}
