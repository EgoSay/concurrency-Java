package com.cjw.concurrency.example.immutable;

import com.cjw.concurrency.annoations.ThreadSafe;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

/**
 * @author codeAC
 * @Date: 2018/8/27
 * @Time: 17:01
 * @Description
 */
@ThreadSafe
@Slf4j
public class ImmutableExample {

    //用final修饰不可变对象
    private final static Integer a = 1;
    private final String b = "2";
    private  static Map<Integer,Integer> map = Maps.newHashMap();

    private final static ImmutableMap<Integer,Integer> map1 = ImmutableMap.of(1,2);
    private final static ImmutableMap<Integer, Integer> map2 =
            ImmutableMap.<Integer,Integer>builder().put(2, 3).build();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
        //Collections包里这个类将map里面的所有方法都抛出异常，因此就不能修改map里面的内容了
       map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
//        map.put(1, 3);
//        log.info("{}", map.get(1));
        log.info("{}",map1.get(1));
    }
}
