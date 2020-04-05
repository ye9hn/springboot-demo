package com.henu.concurrency.example.atomic;

import com.henu.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

/**
 * AtomicReference
 *
 * @author 小陽
 * @since 2019/4/12 22:21
 */
@Slf4j
@ThreadSafe
public class AtomicExample4 {

    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) {
        count.compareAndSet(0, 2);//2
        count.compareAndSet(0, 1);//不执行
        count.compareAndSet(1, 3);//不执行
        count.compareAndSet(2, 4);//4
        count.compareAndSet(3, 5);//不执行
        log.info("count:{}", count.get());
    }
}
