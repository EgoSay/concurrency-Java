package com.cjw.concurrency.example.singleton;

import com.cjw.concurrency.annoations.NotRecommend;
import com.cjw.concurrency.annoations.NotThreadSafe;

/**
 * @author codeAC
 * @Date: 2018/8/27
 * @Time: 15:23
 * @Description: 懒汉模式实现单例
 */
@NotThreadSafe
@NotRecommend
public class SingletonExample3 {

    //构造函数必须为私有的，不能被new创建对象
    private SingletonExample3() {

    }

    private static SingletonExample3 instance = null;

    /**
     * 错误的双重检查锁定
     * 这里代码执行到第一次检查时，读取到对象实例不为空时，因为指令重排序，引用的对象有可能还没有完成初始化
     */
    public static  SingletonExample3 getInstance(){
        if(instance == null) {
            synchronized (SingletonExample3.class){
                if (instance == null) {
                    instance = new SingletonExample3();
                }
            }
        }
        return instance;

    }
}
