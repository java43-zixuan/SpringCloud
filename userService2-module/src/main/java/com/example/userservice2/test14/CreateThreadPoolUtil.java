package com.example.userservice2.test14;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CreateThreadPoolUtil {

    // 最原始的方式创建线程池
    private static final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            3, 9, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(128), Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    public static ThreadPoolExecutor getInstance() {
        return poolExecutor;
    }

}
