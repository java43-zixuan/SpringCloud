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
        // ???????????????????????????????????????:10???,20????????????????????????????????????????????????????????????????????? -> ???????????? -> ???????????? -> ???????????? -> ???????????????????????????
        final boolean tryLock = this.tryLock(lockKey, unit, waitTime, leaseTime);
        final long end = SystemClock.now();
        if (!tryLock) {
            log.error("[{}]???????????????????????????lockKey = {}?????????{}ms", scene, lockKey, end - start);
            throw new ServiceException(ResponseEnum.ACCOUNT_NOT_REGISTER);
        }

        // ????????????????????????????????????????????????try{}finally{?????????}
        try {
            log.info("[{}]???????????????????????????lockKey = {}?????????{}ms", scene, lockKey, end - start);
            return supplier.get();
        } finally {
            this.unlock(lockKey);
        }
    }
}
