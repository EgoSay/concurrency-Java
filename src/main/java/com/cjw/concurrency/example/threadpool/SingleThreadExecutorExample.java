package com.cjw.concurrency.example.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author codeAC
 * @Date: 2018/8/29
 * @Time: 15:58
 * @Description: newSingleThreadExecutor
 * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
 * 核心线程数量 corePoolSize = 1;
 * 最大可创建线程数 maximumPoolSize = 1
 * 阻塞队列  workQueue是LinkedBlockingQueue(无界队列)
 * 可存活时间 keepAliveTime = 0L
 */
@Slf4j
public class SingleThreadExecutorExample {

    public static void main(String[] args) {


        //不仅确保只有一个线程顺序执行任务，也保证线程意外终止后会重新创建一个线程继续执行任务
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(() -> log.info("task:{}",index));
        }
        executorService.shutdown();
    }
}
