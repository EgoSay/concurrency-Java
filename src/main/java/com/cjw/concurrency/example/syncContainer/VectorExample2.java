package com.cjw.concurrency.example.syncContainer;

import com.cjw.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author codeAC
 * @Date: 2018/8/28
 * @Time: 12:37
 * @Description: 在循环遍历集合时不要执行更新操作，或者先做好更新操作标记，遍历完成后再执行更新操作
 */
@Slf4j
@ThreadSafe
public class VectorExample2 {

    //使用foreach循环
    private static void test1(Vector<Integer> v1) {
        for (Integer i : v1) {
            if (i.equals(3)) {
                //抛出异常
                v1.remove(i);
            }
        }
    }

    //使用迭代器iterator
    private static void test2(Vector<Integer> v1) {
        Iterator<Integer> iterator = v1.iterator();
        while (iterator.hasNext()) {
            Integer i = iterator.next();
            if (i.equals(3)) {
                //抛出异常
                v1.remove(i);
            }
        }
    }

    //正常for循环
    private static void test3(Vector<Integer> v1) {
        for (int i = 0; i < v1.size(); i++) {
            if (v1.get(i).equals(3)) {
                v1.remove(i);
            }
        }
    }

    public static void main(String[] args) {

        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        //test1(vector);
        //test2(vector);
        test3(vector);

    }

}
