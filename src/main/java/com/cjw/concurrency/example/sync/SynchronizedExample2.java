package com.cjw.concurrency.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author codeAC
 * @Date: 2018/8/27
 * @Time: 10:58
 * @Description
 */
@Slf4j
public class SynchronizedExample2 {

    //修饰一个类
    public static void test1(int j){
        synchronized (SynchronizedExample1.class) {
            for (int i = 0; i < 10; i++) {
                log.info("test1 {} - {}",j,i);
            }
        }
    }

    //修饰一个静态方法
    private static synchronized void test2(int j){
        for (int i = 0; i < 10; i++) {
            log.info("test2 {} - {}",j,i);
        }
    }

    public static void main(String[] args) {
        SynchronizedExample2 example1 = new SynchronizedExample2();
        SynchronizedExample2 example2 = new SynchronizedExample2();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
//            example1.test1(1);
            example1.test2(1);
//            example1.test2();
        });
        executorService.execute(() -> {
//            example1.test1();
//            example1.test2();
//            example2.test1(2);
            example2.test2(2);
        });
        executorService.shutdown();

    }
}
