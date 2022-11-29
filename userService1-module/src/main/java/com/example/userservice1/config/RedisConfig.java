package com.example.userservice1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description: RedisConfig
 * @Author eric
 * @Date: 2022/7/13 15:31
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {
    @Autowired
    private RedisProperties redisProperties;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(redisProperties.getPool().getMinIdle());
        jedisPoolConfig.setMaxIdle(redisProperties.getPool().getMaxIdle());
        jedisPoolConfig.setMaxTotal(redisProperties.getPool().getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getPool().getMaxWait());

        RedisProperties.Sentinel sentinel = redisProperties.getSentinel();
        //redis哨兵模式
        if (sentinel != null) {
            RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration().master(sentinel.getMaster());
            String[] nodes = sentinel.getNodes().split(",");
            for (int i = 0;i < nodes.length ;i++){
                String[] node = nodes[i].split(":");
                sentinelConfig.sentinel(node[0], Integer.valueOf(node[1]));
            }
            sentinelConfig.setPassword(redisProperties.getPassword());
            sentinelConfig.setDatabase(redisProperties.getDatabase());
            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(sentinelConfig,jedisPoolConfig);
            return jedisConnectionFactory;
            //redis Cluster模式
        } else if(redisProperties.getCluster() != null){
            RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
            clusterConfiguration.setPassword(redisProperties.getPassword());
            clusterConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
            return new JedisConnectionFactory(clusterConfiguration);
        } else {
            //其他模式
            RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
            redisConfiguration.setHostName(redisProperties.getHost());
            redisConfiguration.setPort(redisProperties.getPort());
            redisConfiguration.setPassword(redisProperties.getPassword());
            redisConfiguration.setDatabase(redisProperties.getDatabase());

            JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder().usePooling();
            jedisClientConfiguration.poolConfig(jedisPoolConfig);
            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisConfiguration, jedisClientConfiguration.build());
            return jedisConnectionFactory;
        }
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }



}
