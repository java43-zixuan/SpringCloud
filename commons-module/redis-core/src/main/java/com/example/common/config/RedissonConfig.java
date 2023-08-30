package com.example.common.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@ConfigurationProperties(prefix = "redis")
@Component
@Data
public class RedissonConfig {

    private Cluster cluster;
    private Sentinel sentinel;
    private String host;
    private String port;
    private String password;
    public static class Cluster {
        private List<String> nodes;
        public List<String> getNodes() {
            return nodes;
        }
        public void setNodes(List<String> nodes) {
            this.nodes = nodes;
        }
    }
    public static class Sentinel {
        private List<String> nodes;
        public List<String> getNodes() {
            return nodes;
        }
        public void setNodes(List<String> nodes) {
            this.nodes = nodes;
        }
    }

    @Bean
    public RedissonClient redisson(){
        //创建配置
        Config config = new Config();
        if(cluster != null ){
            //取nodes节点
            List<String> clusterNodes = new ArrayList<>();
            for (int i = 0; i < this.getCluster().getNodes().size(); i++) {
                clusterNodes.add("redis://" + this.getCluster().getNodes().get(i));
            }
            //cluster集群模式配置redissonConfig
            config.useClusterServers().addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]));
        }else if(sentinel != null){
            //取nodes节点
            List<String> sentinelNodes = new ArrayList<>();
            for (int i = 0; i < this.getSentinel().getNodes().size(); i++) {
                sentinelNodes.add("redis://" + this.getSentinel().getNodes().get(i));
            }
            //哨兵模式配置redissonConfig
            config.useSentinelServers().addSentinelAddress(sentinelNodes.toArray(new String[sentinelNodes.size()]))
                    .setMasterName("mymaster")
                    .setCheckSentinelsList(false);
        }else{
            //单机模式
            config.useSingleServer()
                    .setAddress("redis://"+host+":"+port)
                    .setDatabase(0);
            if(StringUtils.isNotBlank(password)){
                config.useSingleServer().setPassword(password);
            }
        }
        return Redisson.create(config);
    }


}