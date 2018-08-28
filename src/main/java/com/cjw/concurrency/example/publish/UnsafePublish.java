package com.cjw.concurrency.example.publish;

import com.cjw.concurrency.annoations.NotThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author codeAC
 * @Date: 2018/8/27
 * @Time: 14:47
 * @Description
 */
@Slf4j
@NotThreadSafe
public class UnsafePublish {

    @Getter private String[] states = {"a", "b", "c"};

    public static void main(String[] args) {
        UnsafePublish unsafePublish = new UnsafePublish();
        log.info("{}",Arrays.toString(unsafePublish.getStates()));

        //可以修改私有变量states的值
        unsafePublish.getStates()[0] = "d";
        log.info("{}",Arrays.toString(unsafePublish.getStates()));
    }
}
