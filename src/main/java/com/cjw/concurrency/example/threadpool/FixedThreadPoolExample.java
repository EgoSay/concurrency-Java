package com.cjw.concurrency.example.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author codeAC
 * @Date: 2018/8/29
 * @Time: 15:58
 * @Description: newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 * 核心线程数量 corePoolSize = maximumPoolSize，为初始化定义传入的参数;
 * 阻塞队列  workQueue是LinkedBlockingQueue(无界队列，当任务耗时较长时可能会导致大量新任务在队列中堆积最终导致OOM)
 * 可存活时间 keepAliveTime = 0L
 */
@Slf4j
public class FixedThreadPoolExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(() -> log.info("task:{}",index));
        }
        executorService.shutdown();
    }
}
