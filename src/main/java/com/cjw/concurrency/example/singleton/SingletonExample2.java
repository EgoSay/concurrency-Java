package com.cjw.concurrency.example.singleton;

import com.cjw.concurrency.annoations.NotRecommend;
import com.cjw.concurrency.annoations.ThreadSafe;

/**
 * @author codeAC
 * @Date: 2018/8/27
 * @Time: 15:23
 * @Description: 懒汉模式实现单例
 */
@ThreadSafe
@NotRecommend
public class SingletonExample2 {

    //构造函数必须为私有的，不能被new创建对象
    private SingletonExample2() {

    }

    private static SingletonExample2 instance = null;
    //这种虽然解决了线程安全问题，但是每次调用获取实例时都需要进行线程锁定判断，性能不好
    synchronized public static  SingletonExample2 getInstance(){
        if(instance == null) {
            instance = new SingletonExample2();
        }
        return instance;

    }
}
