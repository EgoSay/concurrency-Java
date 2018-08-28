package com.cjw.concurrency.example.threadLocal;

/**
 * @author codeAC
 * @Date: 2018/8/28
 * @Time: 8:01
 * @Description: 当使用ThreadLocal维护变量的时候 为每一个使用该变量的线程提供一个独立的变量副本(ThreadLocalMap)
 */
public class RequestHolder {

    private final static ThreadLocal<Long> LONG_THREAD_LOCAL = new InheritableThreadLocal<>();

    //请求进入后端服务器但还没有进行处理时将相关信息存入
    public static void add(Long id) {
        LONG_THREAD_LOCAL.set(id);
    }

    static Long getId() {
        return LONG_THREAD_LOCAL.get();
    }

    //必须remove不然会发生内存泄漏
    public static void remove() {
        LONG_THREAD_LOCAL.remove();
    }
}
