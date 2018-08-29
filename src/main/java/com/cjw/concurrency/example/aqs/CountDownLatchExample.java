package com.cjw.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author codeAC
 * @Date: 2018/8/28
 * @Time: 14:42
 * @Description
 */
@Slf4j
public class CountDownLatchExample {

    private static final int threadCount = 20;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    test(threadNum);
                } catch (InterruptedException e) {
                    log.error("exception",e);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        //countDownLatch.await(); 正常执行完所有
        countDownLatch.await(10,TimeUnit.MILLISECONDS); //设置超时
        log.info("finish");
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {

        Thread.sleep(100);

        log.info("{}",threadNum);
    }
}
