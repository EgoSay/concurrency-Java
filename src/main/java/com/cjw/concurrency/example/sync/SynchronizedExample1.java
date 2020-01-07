package com.cjw.concurrency.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author codeAC
 * @Date: 2018/8/27
 * @Time: 10:58
 * @Description
 */
@Slf4j
public class SynchronizedExample1 {

    // 修饰一个代码块
    public void test1(int j){
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                log.info("test1 {} - {}",j,i);
            }
        }
    }

    //修饰一个方法
    public synchronized void test2(int j){
        for (int i = 0; i < 10; i++) {
            log.info("test2 {} - {}",j,i);
        }
    }

    public static void main(String[] args) {
        SynchronizedExample1 example1 = new SynchronizedExample1();
        SynchronizedExample1 example2 = new SynchronizedExample1();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1, 2,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());

        threadPoolExecutor.execute(() -> {
            example1.test1(1);
            // example2.test2(1);
        });
        threadPoolExecutor.execute(() -> {
            example1.test1(2);
            // example2.test2(2);
        });
        threadPoolExecutor.shutdown();

    }
}
