package com.cjw.concurrency.example.singleton;

import com.cjw.concurrency.annoations.ThreadSafe;

/**
 * @author codeAC
 * @Date: 2018/8/27
 * @Time: 15:23
 * @Description: 饿汉模式实现单例，实例在类装载时进行创建，适用于构造函数中没有进行太多操作的情况下
 */
@ThreadSafe
public class SingletonExample1 {

    //构造函数必须为私有的，不能被new创建对象
    private SingletonExample1() {

    }

    private static SingletonExample1 instance = new SingletonExample1();

    public static SingletonExample1 getInstance(){
        return instance;
    }
}
