package com.cjw.concurrency.example.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author codeAC
 * @Date: 2018/8/29
 * @Time: 15:58
 * @Description: newCachedThreadPool,在有任务时才创建新线程，空闲线程存活60s
 * 核心线程数量 corePoolSize = 0;
 * 最大可创建线程数为整形变量最大值 maximumPoolSize = Integer.MAX_VALUE
 * 阻塞队列  workQueue是SynchronousQueue(队列中只能有一个元素)
 * 可存活时间 keepAliveTime = 60L
 */
@Slf4j
public class CachedThreadPoolExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(() -> log.info("task:{}",index));
        }
        executorService.shutdown();
    }
}
