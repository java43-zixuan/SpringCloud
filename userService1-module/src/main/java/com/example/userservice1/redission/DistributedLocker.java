package com.example.userservice1.redission;

import org.redisson.api.RLock;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public interface DistributedLocker {
    RLock lock(String lockKey);

    RLock lock(String lockKey, long timeout);

    RLock lock(String lockKey, TimeUnit unit, long timeout);

    boolean tryLock(String lockKey, TimeUnit unit, long leaseTime);

    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);

    /**
     * @param lockKey
     * @param unit
     * @param waitTime
     * @param leaseTime
     * @param supplier 获取锁后要执行的业务逻辑
     * @param scene 业务逻辑的场景，用于打印日志
     * @param <T>
     * @return
     */
    <T> T tryLockAndRun(@Nonnull String lockKey, @Nonnull TimeUnit unit, long waitTime, long leaseTime, @Nonnull Supplier<T> supplier, String scene);
}
