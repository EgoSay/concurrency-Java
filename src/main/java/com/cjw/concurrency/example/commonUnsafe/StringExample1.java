package com.cjw.concurrency.example.commonUnsafe;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author codeAC
 * @Date: 2018/8/28
 * @Time: 8:58
 * @Description: 测试StringBuilder(不安全)和StringBuffer
 */
@Slf4j
public class StringExample1 {

    //请求总数
    private static int clientTotal = 5000;

    //同时并发执行的线程数
    private static  int threadTotal = 200;

    private static  StringBuilder stringBuilder = new StringBuilder();
    private static StringBuffer stringBuffer = new StringBuffer();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update();
                    semaphore.release();
                } catch (InterruptedException e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("\nsizeOfStringBuilder:{}\nsizeOfStringBuffer:{}",stringBuilder.length(),stringBuffer.length());
    }
    private static void update() {
        stringBuilder.append("1");
        stringBuffer.append("2");
    }
}
