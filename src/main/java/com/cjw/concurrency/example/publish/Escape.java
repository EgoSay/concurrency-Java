package com.cjw.concurrency.example.publish;

import com.cjw.concurrency.annoations.NotRecommend;
import com.cjw.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * @author codeAC
 * @Date: 2018/8/27
 * @Time: 15:06
 * @Description: 对象逸出，当一个对象还没有构造完成的时候就使他被其他线程所见
 */
@Slf4j
@NotThreadSafe
@NotRecommend
public class Escape {
    private int thisIsCanBeEscaped = 0;
    private Escape(){
        new Innerclass();
    }

    private class Innerclass {
        private Innerclass(){
            log.info("{}",Escape.this.thisIsCanBeEscaped);
        }
    }

    public static void main(String[] args) {
        new Escape();
    }
}
