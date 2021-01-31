package com.cjw.concurrency.example.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * refer: https://xie.infoq.cn/article/08d2ca248c3779efa332c6d53
 * 自定义安全线程类(阿里线程池创建规范)
 * @author Ego
 * @version 1.0
 * @date 2021/1/31 22:17
 * @since JDK1.8
 */
public class SafeExecutors {

    /**
     *
     * @param corePoolSize the number of threads to keep in the pool, even
     *        if they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param maxPoolSize the maximum number of threads to allow in the
     *        pool
     * @param queueCapacity the queue to use for holding tasks before they are
     *        executed.  This queue will hold only the {@code Runnable}
     *        tasks submitted by the {@code execute} method.
     * @param threadNamePrefix a {@link String#format(String, Object...)}-compatible
     *     format String, to which a unique integer (0, 1, etc.) will be supplied
     *     as the single parameter. This integer will be unique to the built
     *     instance of the ThreadFactory and will be assigned sequentially. For
     *     example, {@code "rpc-pool-%d"} will generate thread names like
     *     {@code "rpc-pool-0"}, {@code "rpc-pool-1"}, {@code "rpc-pool-2"}, etc.
     * @return CachedThreadPool
     */
    public static ExecutorService newCachedThreadPool(int corePoolSize, int maxPoolSize,
                                                      int queueCapacity, String threadNamePrefix) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").build();
        LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(queueCapacity);
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, 60L, TimeUnit.SECONDS, blockingQueue, namedThreadFactory);
    }

    /**
     *
     * @param corePoolSize the number of threads to keep in the pool, even
     *        if they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param queueCapacity the queue to use for holding tasks before they are
     *        executed.  This queue will hold only the {@code Runnable}
     *        tasks submitted by the {@code execute} method.
     * @param threadNamePrefix a {@link String#format(String, Object...)}-compatible
     *     format String, to which a unique integer (0, 1, etc.) will be supplied
     *     as the single parameter. This integer will be unique to the built
     *     instance of the ThreadFactory and will be assigned sequentially. For
     *     example, {@code "rpc-pool-%d"} will generate thread names like
     *     {@code "rpc-pool-0"}, {@code "rpc-pool-1"}, {@code "rpc-pool-2"}, etc.
     * @return FixedThreadPool
     */
    public static ExecutorService newFixedThreadPool(int corePoolSize, int queueCapacity, String threadNamePrefix) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").build();
        LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(queueCapacity);
        return new ThreadPoolExecutor(corePoolSize, corePoolSize, 60L, TimeUnit.SECONDS, blockingQueue, namedThreadFactory);
    }

    /**
     *
     * @param queueCapacity the queue to use for holding tasks before they are
     *        executed.  This queue will hold only the {@code Runnable}
     *        tasks submitted by the {@code execute} method.
     * @param threadNamePrefix a {@link String#format(String, Object...)}-compatible
     *     format String, to which a unique integer (0, 1, etc.) will be supplied
     *     as the single parameter. This integer will be unique to the built
     *     instance of the ThreadFactory and will be assigned sequentially. For
     *     example, {@code "rpc-pool-%d"} will generate thread names like
     *     {@code "rpc-pool-0"}, {@code "rpc-pool-1"}, {@code "rpc-pool-2"}, etc.
     * @return SingledThreadPool
     */
    public static ExecutorService newSingledThreadPool(int queueCapacity, String threadNamePrefix) {
        return newFixedThreadPool(1, queueCapacity, threadNamePrefix);
    }

    /**
     *
     * @param corePoolSize the number of threads to keep in the pool, even
     *        if they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param maxPoolSize the maximum number of threads to allow in the
     *        pool
     * @param queueCapacity the queue to use for holding tasks before they are
     *        executed.  This queue will hold only the {@code Runnable}
     *        tasks submitted by the {@code execute} method.
     * @param threadNamePrefix a {@link String#format(String, Object...)}-compatible
     *     format String, to which a unique integer (0, 1, etc.) will be supplied
     *     as the single parameter. This integer will be unique to the built
     *     instance of the ThreadFactory and will be assigned sequentially. For
     *     example, {@code "rpc-pool-%d"} will generate thread names like
     *     {@code "rpc-pool-0"}, {@code "rpc-pool-1"}, {@code "rpc-pool-2"}, etc.
     * @return  ScheduledThreadPool
     */
    public static ExecutorService newScheduledThreadPool(int corePoolSize, int maxPoolSize,
                                                         int queueCapacity, String threadNamePrefix) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").build();
        return new SafeScheduledThreadPool(corePoolSize, maxPoolSize, queueCapacity, namedThreadFactory);
    }

    static class SafeScheduledThreadPool extends ScheduledThreadPoolExecutor {
        protected final int queueCapacity;

        public SafeScheduledThreadPool(int corePoolSize, int maxPoolSize, int queueCapacity, ThreadFactory threadFactory) {
            super(corePoolSize, threadFactory);
            super.setMaximumPoolSize(maxPoolSize);
            this.queueCapacity = queueCapacity;
        }

        @Override
        public ScheduledFuture<?> schedule(Runnable command,
                                           long delay,
                                           TimeUnit unit) {
            checkQueueSize();
            return super.schedule(command, delay, unit);
        }

        //省略覆写ScheduledThreadPoolExecutor中其他定时任务方法，实现同上

        private void checkQueueSize() {
            if (getQueue().size() >= queueCapacity) {

                throw new RejectedExecutionException("" + "DelayedWorkQueue size exceed capacity: " + queueCapacity);
            }
        }
    }
}

