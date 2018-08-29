package com.cjw.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author codeAC
 * @Date: 2018/8/29
 * @Time: 10:41
 * @Description:
 */
@Slf4j
public class FutureExample {

    //实现callable接口可以自定义回调任务
    static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            log.info("do something in callable");
            Thread.sleep(1000);
            return "Done";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //实现Future接口可以获取任务执行结果
        Future<String> future = executorService.submit(new MyCallable());
        log.info("do something in main");
        Thread.sleep(1000);
        String result = future.get();
        log.info("result:{}",result);
        executorService.shutdown();
    }
}
