package com.cjw.concurrency.example.singleton;

import com.cjw.concurrency.annoations.Recommend;
import com.cjw.concurrency.annoations.ThreadSafe;

/**
 * @author codeAC
 * @Date: 2018/8/27
 * @Time: 15:23
 * @Description: 利用枚举实现
 */
@ThreadSafe
@Recommend
public class SingletonExample6 {

    //构造函数必须为私有的，不能被new创建对象
    private SingletonExample6() {

    }

    public static SingletonExample6 getInstance(){
        return Singleton.INSTANCE.getSingleton();
    }

    private enum Singleton {
        INSTANCE;
        private SingletonExample6 singleton;
        //JVM保证这个方法只会被调用一次
        Singleton() {
            singleton = new SingletonExample6();
        }

        public SingletonExample6 getSingleton(){
            return singleton;
        }
    }
}
