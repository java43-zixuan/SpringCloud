package com.example.userservice1.redission;

import cn.hutool.core.date.SystemClock;
import com.example.common.exception.ServiceException;
import com.example.common.response.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Component()
public class RedissonDistributedLocker implements DistributedLocker {
    @Autowired
    private RedissonClient redissonClient;

    public RLock lock(String lockKey) {
        RLock lock = this.redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    public RLock lock(String lockKey, long timeout) {
        RLock lock = this.redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
        return lock;
    }

    public RLock lock(String lockKey, TimeUnit unit, long timeout) {
        RLock lock = this.redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    public boolean tryLock(String lockKey, TimeUnit unit, long leaseTime) {
        RLock lock = this.redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(0L, leaseTime, unit);
        } catch (InterruptedException var7) {
            return false;
        }
    }

    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime){
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void unlock(String lockKey){
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (lock.isLocked()) {
                lock.unlock();
            }
        } catch (IllegalMonitorStateException localIllegalMonitorStateException) {}
    }

    public void unlock(RLock lock) {
        try {
            if (lock.isLocked()) {
                lock.unlock();
            }
        } catch (IllegalMonitorStateException var3) {}
    }

    @Override
    public <T> T tryLockAndRun(@Nonnull String lockKey, @Nonnull TimeUnit unit, long waitTime, long leaseTime, @Nonnull Supplier<T> supplier, String scene) {
        final long start = SystemClock.now();
        // 获取分布式锁，最长等待时间:10秒,20秒后自动释放。注意锁与事务的顺序：获取分布式锁 -> 开启事务 -> 执行业务 -> 提交事务 -> 释放分布式锁！！！
        final boolean tryLock = this.tryLock(lockKey, unit, waitTime, leaseTime);
        final long end = SystemClock.now();
        if (!tryLock) {
            log.error("[{}]获取分布式锁失败，lockKey = {}，耗时{}ms", scene, lockKey, end - start);
            throw new ServiceException(ResponseEnum.ACCOUNT_NOT_REGISTER);
        }

        // 注意：一定是获取锁成功后，才进行try{}finally{释放锁}
        try {
            log.info("[{}]获取分布式锁成功，lockKey = {}，耗时{}ms", scene, lockKey, end - start);
            return supplier.get();
        } finally {
            this.unlock(lockKey);
        }
    }
}
