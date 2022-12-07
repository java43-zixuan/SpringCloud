package com.example.userservice2.test17;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorLock {
    public static void main(String[] args) throws Exception {
        //创建zookeeper的客户端:重试策略，初始化每次重试之间需要等待的时间，基准等待时间为1秒。
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181,localhost:2182,localhost:2183", retryPolicy);
        client.start();
        //创建分布式锁, 锁空间的根节点路径为/curator/lock_node
        InterProcessMutex mutex = new InterProcessMutex(client, "/lock_node");
        mutex.acquire();
        //获得了锁, 进行业务流程
        System.out.println("Enter mutex");
        //完成业务流程, 释放锁
        mutex.release();
        //关闭客户端
        client.close();
    }
}