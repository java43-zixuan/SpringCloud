package com.example.kafkamodule.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZH
 */
@Configuration
@EnableKafka
public class KafkaConfig {
    /**
     * kafka生产者配置
     */
    //kafka服务地址
    @Value("${spring.kafka.producer.bootstrap.servers:localhost:9092}")
    private String producerServers;
    //key的序列化规则
    @Value("${spring.kafka.producer.key.serializer.class:org.apache.kafka.common.serialization.StringSerializer}")
    private String producerKeySerializerClass;
    //value的序列化规则
    @Value("${spring.kafka.producer.value.serializer.class:org.apache.kafka.common.serialization.StringSerializer}")
    private String producerValueSerializerClass;
    //（默认16KB）一批次占用的内存大小
    @Value("${spring.kafka.producer.batch.size:200}")
    private int batchSize;
    //（默认32MB）生产者内存缓冲区的大小,生产者缓冲要发送到服务器的数据
    @Value("${spring.kafka.producer.buffer.memory:33554432}")
    private int bufferMemory;
    //生产者请求超时时间
    @Value("${spring.kafka.producer.request.timeout.ms:30000}")
    private int producerRequestTimeoutMs;
    //（默认0）leader挂了，发送消息重试的次数
    @Value("${spring.kafka.producer.retries:0}")
    private int retries;
    //（默认0）在消息批次发送出去之前等待更多消息加入该批次的时间
    @Value("${spring.kafka.producer.linger.ms:1}")
    private int lingerMs;


    //kafka服务地址
    @Value("${spring.kafka.consumer.bootstrap.servers:localhost:9092}")
    private String consumerServers;
    //消费者请求超时时间
    @Value("${spring.kafka.consumer.request.timeout.ms:30000}")
    private int consumerRequestTimeoutMs;
    //消费者启动多个线程去监听消息
    //1、concurrency要根据实际情况设置，一个消费端的情况下设置的值不要大于分区数。
    //2、多个消费端的情况下，如果消费端数量>=分区数，就没有必要再去设置concurrency（concurrency默认值为1）。
    @Value("${spring.kafka.consumer.concurrency.size:1}")
    private int concurrencySize;
    //消费者在被认为死亡之前可以与服务器断开连接的时间，默认是 3s。
    @Value("${spring.kafka.consumer.session.timeout.ms:15000}")
    private String sessionTimeoutMs;
    //消费者是否自动提交偏移量，默认值是 true。为了避免出现重复消费和数据丢失，可以把它设置为 false。
    @Value("${spring.kafka.consumer.enable.auto.commit:false}")
    private boolean enableAutoCommit;
    //消费者自动提交周期，默认5S
    @Value("${spring.kafka.consumer.auto.commit.interval.ms:500}")
    private String autoCommitIntervalMs;
    //消费者组ID
    @Value("${spring.kafka.consumer.group.id:groupIdNotSet}")
    private String groupId;
    //key的序列化规则
    @Value("${spring.kafka.consumer.key.serializer.class:org.apache.kafka.common.serialization.StringDeserializer}")
    private String consumerKeySerializerClass;
    //value的序列化规则
    @Value("${spring.kafka.consumer.value.serializer.class:org.apache.kafka.common.serialization.StringDeserializer}")
    private String consumerValueSerializerClass;
    //该属性指定了消费者在读取一个没有偏移量的分区或者偏移量无效的情况下该作何处理：
    //latest (默认值) ：在偏移量无效的情况下，消费者将从最新的记录开始读取数据（在消费者启动之后生成的最新记录）;
    //earliest ：在偏移量无效的情况下，消费者将从起始位置读取分区的记录。
    @Value("${spring.kafka.consumer.auto.offset.reset:latest}")
    protected String autoReset;
    //消费者单次调用 poll() 方法能够返回的记录数量。
    @Value("${spring.kafka.consumer.max.poll.records:500}")
    private int maxPollRecords;
    //消费者调用poll()最大拉取时间，在拉取消息时如果超过了设置的最大拉取时间，则会认为消费者消费消息失败
    @Value("${spring.kafka.consumer.max.poll.interval.ms:300000}")
    private int maxPollIntervalMs;

    public KafkaConfig() {
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(this.consumerFactory());
        factory.setConcurrency(this.concurrencySize);
        factory.getContainerProperties().setPollTimeout((long) this.consumerRequestTimeoutMs);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory(this.consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap(16);
        props.put("bootstrap.servers", this.consumerServers);
        props.put("group.id", this.groupId);
        props.put("enable.auto.commit", this.enableAutoCommit);
        props.put("auto.commit.interval.ms", this.autoCommitIntervalMs);
        props.put("session.timeout.ms", this.sessionTimeoutMs);
        props.put("key.deserializer", this.consumerKeySerializerClass);
        props.put("value.deserializer", this.consumerValueSerializerClass);
        props.put("request.timeout.ms", this.consumerRequestTimeoutMs);
        props.put("auto.offset.reset", this.autoReset);
        props.put("max.poll.records", this.maxPollRecords);
        props.put("max.poll.interval.ms", this.maxPollIntervalMs);
        return props;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory(this.producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap(12);
        props.put("bootstrap.servers", this.producerServers);
        props.put("retries", this.retries);
        props.put("batch.size", this.batchSize);
        props.put("linger.ms", this.lingerMs);
        props.put("buffer.memory", this.bufferMemory);
        props.put("key.serializer", this.producerKeySerializerClass);
        props.put("value.serializer", this.producerValueSerializerClass);
        props.put("request.timeout.ms", this.producerRequestTimeoutMs);
        return props;
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate(this.producerFactory());
    }
}
