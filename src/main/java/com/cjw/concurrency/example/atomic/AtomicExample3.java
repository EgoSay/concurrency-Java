package com.cjw.concurrency.example.atomic;

import com.cjw.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author codeAC
 * @Date: 2018/8/27
 * @Time: 8:02
 * @Description: 实验AtomicReference，对某个对象进行原子操作
 */
@Slf4j
@ThreadSafe
public class AtomicExample3 {
    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) {
        count.compareAndSet(0, 2);
        count.compareAndSet(0, 1);
        count.compareAndSet(1, 3);
        count.compareAndSet(2, 4);
        count.compareAndSet(3, 5);
        log.info("count:{}",count.get());
    }
}
