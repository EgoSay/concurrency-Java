package com.cjw.concurrency.example.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author codeAC
 * @Date: 2018/8/29
 * @Time: 15:58
 * @Description: newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
 * 核心线程数量 corePoolSize 为指定参数;
 * 最大可创建线程数为整形变量最大值 maximumPoolSize = Integer.MAX_VALUE
 * 阻塞队列  workQueue是DelayedWorkQueue(无界队列，保证添加到队列中的任务，会按照任务的延时时间进行排序，延时时间少的任务首先被获取)
 * 可存活时间 keepAliveTime = 0L
 */
@Slf4j
public class ScheduledThreadPoolExample {

    public static void main(String[] args) {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);


        executorService.schedule(() -> log.info("schedule run"),3,TimeUnit.SECONDS);

        //按照以固定的某个频率来执行任务，不受计划执行时间的影响。到时间，它就执行
        //executorService.scheduleAtFixedRate(() -> log.info("schedule run"),1,3,TimeUnit.SECONDS);

        //按照指定的延时时间执行，无论某个任务执行多长时间，等执行完了，再延迟指定的时间执行，受计划执行时间的影响
        //executorService.scheduleWithFixedDelay()

        executorService.shutdown();
    }
}
