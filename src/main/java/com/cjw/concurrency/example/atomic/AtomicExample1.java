package com.cjw.concurrency.example.atomic;

import com.cjw.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author codeAC
 * @Date: 2018/8/24
 * @Time: 12:57
 * @Description: LongAdder计数,将热点数据分散到一个数组，利用hash算法映射，最后统计的数据为数组之和
 */
@Slf4j
@ThreadSafe
public class AtomicExample1 {
    //请求总数
    private static int clientTotal = 5000;

    //同时并发执行的线程数
    private static  int threadTotal = 200;

    private static LongAdder count = new LongAdder();

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
        log.info("count:{}",count);
    }
    private static void add() {
        count.increment();
    }
}
