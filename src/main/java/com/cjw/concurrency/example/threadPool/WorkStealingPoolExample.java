package com.cjw.concurrency.example.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author codeAC
 * @Date: 2018/8/29
 * @Time: 17:02
 * @Description: newWorkStealingPool创建一个带并行级别的线程池，并行级别决定了同一时刻最多有多少个线程在执行
 */

@Slf4j
public class WorkStealingPoolExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newWorkStealingPool(3);
        for (int i = 0; i < 10; i++) {
            final int count = i;
            executorService.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("thread{}, {}",Thread.currentThread().getId(),count);
            });
        }
        while (true) {

        }
    }
}
