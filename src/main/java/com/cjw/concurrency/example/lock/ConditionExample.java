package com.cjw.concurrency.example.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author codeAC
 * @Date: 2018/8/29
 * @Time: 9:58
 * @Description: Condition的本质就是等待队列和同步队列的交互
 */
@Slf4j
public class ConditionExample {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        new Thread(() -> {
            try {
                reentrantLock.lock();
                log.info("wait signal"); // 1.等待信号
                condition.await();
            } catch (InterruptedException e) {
                log.error("exception",e);
            }finally {
                reentrantLock.unlock();
            }
            log.info("get signal");  //4.获得信号
        }).start();

        new Thread(() -> {
            try {
                reentrantLock.lock();
                log.info("get lock");   //2.获取到锁
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition.signalAll();
            log.info("send signal ~"); //3.发送信号唤醒等待队列中的任务
            reentrantLock.unlock();
        }).start();
    }
}
