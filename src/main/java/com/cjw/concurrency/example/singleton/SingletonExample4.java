package com.cjw.concurrency.example.singleton;

import com.cjw.concurrency.annoations.ThreadSafe;

/**
 * @author codeAC
 * @Date: 2018/8/27
 * @Time: 15:23
 * @Description: 懒汉模式实现单例，基于volatile的解决方案
 */
@ThreadSafe
public class SingletonExample4 {

    //构造函数必须为私有的，不能被new创建对象
    private SingletonExample4() {

    }

    private volatile static SingletonExample4 instance = null;

    public  static  SingletonExample4 getInstance(){
        if(instance == null) {
            synchronized (SingletonExample4.class){
                if (instance == null) {
                    instance = new SingletonExample4();
                }
            }
        }
        return instance;

    }
}
