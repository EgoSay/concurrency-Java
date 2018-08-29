package com.cjw.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author codeAC
 * @Date: 2018/8/28
 * @Time: 15:39
 * @Description: 类似countDownLatch
 * 不同的是后者是等待其他线程执行后再操作，这里是一些线程间相互等待全部就绪后再一起操作
 */
@Slf4j
public class CyclicBarrierExample2 {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executor.execute(() -> {
                try {
                    race(threadNum);
                } catch (Exception e) {
                    log.error("exception",e);
                }
            });
        }
        executor.shutdown();
    }

    private static void race(int threadNum) throws InterruptedException {
        Thread.sleep(1000);
        log.info("{} is ready",threadNum);
        try {
            cyclicBarrier.await(2000,TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.warn("exception",e);
        }
        log.info("{} continue",threadNum);
    }
}
