package com.cjw.concurrency.example.atomic;

import com.cjw.concurrency.annoations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author codeAC
 * @Date: 2018/8/27
 * @Time: 8:12
 * @Description: 实验AtomicIntegerFieldUpdater,对某个对象的属性字段进行原子操作
 */
@Slf4j
@ThreadSafe
public class AtomicExample4 {

    private static final AtomicIntegerFieldUpdater<AtomicExample4> updater =
            AtomicIntegerFieldUpdater.newUpdater(AtomicExample4.class, "count");

    //必须用volatile关键字修饰
    @Getter private volatile int count = 100;

    public static void main(String[] args) {
        AtomicExample4 example4 = new AtomicExample4();
        if (updater.compareAndSet(example4, 100, 120)){
            log.info("First update success, {}",example4.getCount());
        }
        if (updater.compareAndSet(example4, 100, 120)){
            log.info("Second update success, {}",example4.getCount());
        }else {
            log.info("Second update failed,{}",example4.getCount());
        }

    }
}
