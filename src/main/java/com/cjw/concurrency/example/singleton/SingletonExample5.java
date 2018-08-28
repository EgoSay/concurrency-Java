package com.cjw.concurrency.example.singleton;

import com.cjw.concurrency.annoations.ThreadSafe;

/**
 * @author codeAC
 * @Date: 2018/8/27
 * @Time: 15:23
 * @Description: 懒汉模式实现单例
 * 基于类的初始化解决方案
 * JVM在类的初始化阶段(即在Class被加载后，且被线程使用前）,会执行类的初始化
 * 在这期间，JVM回去获取一个锁，这个锁可以同步多个线程对同一个类的初始化
 */

@ThreadSafe
public class SingletonExample5 {

    //构造函数必须为私有的，不能被new创建对象
    private SingletonExample5() {

    }


    private static class InstanceHolder {
        private static SingletonExample5 instance = new SingletonExample5();
    }


    public  static SingletonExample5 getInstance(){
        return InstanceHolder.instance;
    }
}
