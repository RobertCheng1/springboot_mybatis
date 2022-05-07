package com.example.springboot_mybatis.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: MyExecutorPool
 * @Date: 2022/5/6 13:38
 */

@Slf4j
public class MyThreadPool {
    /*
     * 在项目中启动线程池，以方便异步执行某些任务：比如 SQL 变更表结构等
     */

    /**
     * CORE_SIZE
     */
    private static final int CORE_SIZE = 4;
    /**
     * MAX_POOL_SIZE
     */
    private static final int MAX_POOL_SIZE = CORE_SIZE * 20;
    /**
     * ATOMIC
     */
    private static final AtomicInteger POOL_NUMBER = new AtomicInteger();
    /**
     * must be carefully
     */
    private static final ExecutorService executorService = new ThreadPoolExecutor(CORE_SIZE, (CORE_SIZE * 2),
            (300), TimeUnit.SECONDS, new LinkedBlockingQueue<>(MAX_POOL_SIZE), MyThreadPool::newThread);

    private static Thread newThread(Runnable runnable) {
        Thread t = new Thread(runnable);
        t.setName("MyThreadPool_" + POOL_NUMBER.incrementAndGet());
        return t;
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return executorService.submit(task);
    }

    public static Future<?> submit(Runnable task) {
        return executorService.submit(task);
    }
}
