package com.cjw.concurrency.example.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author codeAC
 * @Date: 2018/8/28
 * @Time: 19:19
 * @Description
 */
@Slf4j
public class ReentrantReadWriteLockExample {

    private final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final static Map<Integer, Integer> map = new TreeMap<>();

    private final static Lock readLock = lock.readLock();

    private final static Lock writeLock = lock.writeLock();

    public static Integer get(Integer key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public static Set<Integer> getAllKeys() {
        readLock.lock();
        try {
            return map.keySet();
        } finally {
            readLock.unlock();
        }
    }

    public static Integer put(Integer key, Integer value) {
        writeLock.lock();
        try {
            return map.put(key, value);
        }finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 1; i <= 10; i++) {
            final Integer num = i;
            Thread.sleep(1000);
            executorService.execute(() -> {

                log.info("thread{}",num);

                put(num, num);

                countDownLatch.countDown();

            });
        }
        countDownLatch.await();
        log.info("{}", getAllKeys());
        log.info("{}", get(1));
        executorService.shutdown();
    }
}
