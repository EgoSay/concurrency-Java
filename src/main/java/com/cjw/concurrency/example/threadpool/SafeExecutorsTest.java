package com.cjw.concurrency.example.threadpool;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Ego
 * @version 1.0
 * @date 2021/1/31 22:18
 * @since JDK1.8
 */
public class SafeExecutorsTest {

    @Test(expected = RejectedExecutionException.class)
    public void testSafeNewCachedThreadPool() {
        ExecutorService executorService = SafeExecutors.newCachedThreadPool(5, 20, 10, "test");
        executeSafeSleepTaskLoop(executorService);
    }

    @Test(expected = RejectedExecutionException.class)
    public void testSafeNewFixedThreadPool() {
        ExecutorService executorService = SafeExecutors.newFixedThreadPool(10, 20, "test");
        executeSafeSleepTaskLoop(executorService);
    }

    @Test(expected = RejectedExecutionException.class)
    public void testSafeNewSingledThreadPool() {
        ExecutorService executorService = SafeExecutors.newSingledThreadPool(10, "test");
        executeSafeSleepTaskLoop(executorService);
    }

    @Test(expected = RejectedExecutionException.class)
    public void testSafeNewScheduledThreadPool() {
        ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService)SafeExecutors.newScheduledThreadPool(10, 100, 50, "test");
        try {
            ThreadGroup threadGroup = new ThreadGroup("testSafeNewScheduledThreadPool");
            for(int i = 0; i < 1000; i++) {
                scheduledExecutorService.schedule(new SleepTask(2, threadGroup), 3, TimeUnit.MILLISECONDS);
                System.out.println(scheduledExecutorService.toString());
            }
        } catch (RejectedExecutionException e) {
            System.out.println(e.toString());
            throw e;
        }
    }

    private void executeSafeSleepTaskLoop(ExecutorService executorService) {
        try {

            ThreadGroup threadGroup = new ThreadGroup("test-group");
            for(int i = 0; i < 100000; i++) {
                executorService.submit(new SleepTask(3000, threadGroup));
                System.out.println(executorService.toString());
            }
        } catch (RejectedExecutionException e) {
            System.out.println(e.toString());
            throw e;
        }
    }

    static class SleepTask implements Runnable {
        private final long sleepMilliSeconds;
        private final ThreadGroup threadGroup;

        public SleepTask(long sleepMilliSeconds, ThreadGroup threadGroup) {
            this.sleepMilliSeconds = sleepMilliSeconds;
            this.threadGroup = threadGroup;
        }
        public void run() {
            System.out.println("--------ThreadGroup:" + threadGroup + ",Start task: " + Thread.currentThread().getName());
            try {
                Thread.sleep(sleepMilliSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("--------ThreadGroup:" + threadGroup + ",Finish task: " + Thread.currentThread().getName());
        }
    }
}
