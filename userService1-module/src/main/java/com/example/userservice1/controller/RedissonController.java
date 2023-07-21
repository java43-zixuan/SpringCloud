//package com.example.userservice1.controller;
//
//import io.swagger.annotations.Api;
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Objects;
//
//@Slf4j
//@Api(tags = "redission分布式锁")
//@RestController
//@RequestMapping("/redission")
//public class RedissonController {
//
//    @Autowired
//    private RedissonClient redissonClient;
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//
//    private static final String REDIS_KEY = "redis_oa_01";
//
//    private static final int MAX_SIZE = 100;
//
//    /**
//     * 初始化库存
//     */
//    @PostMapping("/init")
//    public void init() {
//        System.out.println("库存初始化");
//        stringRedisTemplate.opsForValue().set(REDIS_KEY, String.valueOf(MAX_SIZE));
//        System.out.println("库存初始化完毕:"+stringRedisTemplate.opsForValue().get(REDIS_KEY));
//    }
//
//    /**
//     * 扣库存业务
//     */
//    @PostMapping("/exportInventory")
//    public void exportInventory() {
//
//        String lockKey = "product001";
//        RLock lock = redissonClient.getLock(lockKey);
//        try {
//            lock.lock();//加锁
//            int stock =Integer.parseInt(stringRedisTemplate.opsForValue().get(REDIS_KEY));
//            if(stock > 0){
//                int realStock = stock - 1;
//                stringRedisTemplate.opsForValue().set(REDIS_KEY, String.valueOf(realStock));
//                System.out.println("库存扣减成功，剩余库存："+realStock);
//            }else{
//                System.out.println("库存扣减失败，库存不足");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();//释放锁
//        }
//    }
//
//}
