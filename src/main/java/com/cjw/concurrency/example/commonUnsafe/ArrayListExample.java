package com.cjw.concurrency.example.commonUnsafe;

import com.cjw.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author codeAC
 * @Date: 2018/8/28
 * @Time: 10:17
 * @Description
 */
@Slf4j
@NotThreadSafe
public class ArrayListExample {

    //请求总数
    private static int clientTotal = 5000;

    //同时并发执行的线程数
    private static  int threadTotal = 200;

    private static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("size:{}",list.size());
    }
    private static void add() {
        list.add(1);
    }
}
