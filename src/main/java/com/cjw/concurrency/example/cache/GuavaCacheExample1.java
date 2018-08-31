package com.cjw.concurrency.example.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author codeAC
 * @Date: 2018/8/30
 * @Time: 14:29
 * @Description: GuavaCache是谷歌的一个工具库，实现对本地内存的缓存
 */
@Slf4j
public class GuavaCacheExample1 {

    public static void main(String[] args) {
        LoadingCache<String,Integer> cache = CacheBuilder.newBuilder()
                .maximumSize(10) //最多存放10个数据
                .expireAfterWrite(10,TimeUnit.SECONDS) //缓存10秒
                .recordStats()   //开启记录状态数据功能
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) {
                        return -1;
                    }
                });
        log.info("{}",cache.getIfPresent("key1"));  //null
        cache.put("key1", 1); //添加缓存
        log.info("{}",cache.getIfPresent("key1"));  //1
        cache.invalidate("key1"); //将缓存置为无效
        log.info("{}",cache.getIfPresent("key1"));  //null

        try {
            log.info("{}", cache.get("key2")); // -1
            cache.put("key2", 2);
            log.info("{}", cache.get("key2")); // 2

            log.info("{}", cache.size()); // 1

            for (int i = 3; i < 13; i++) {
                cache.put("key" + i, i);
            }
            log.info("{}", cache.size()); // 10

            log.info("{}", cache.getIfPresent("key2")); // null

            Thread.sleep(11000);

            log.info("{}", cache.get("key5")); // -1

            log.info("{},{}", cache.stats().hitCount(), cache.stats().missCount());

            log.info("{},{}", cache.stats().hitRate(), cache.stats().missRate());
        } catch (Exception e) {
            log.error("cache exception", e);
        }
    }
}
