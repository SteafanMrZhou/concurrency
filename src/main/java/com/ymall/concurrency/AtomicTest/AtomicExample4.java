package com.ymall.concurrency.AtomicTest;

import com.ymall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

/**
 * AtomicReference<T> count
 * count.compareAndSet(expect, update);
 * expect-期望值
 * update-更新期望值的值
 * Created by Steafan on 2018/4/27 0027.
 */
@Slf4j
@ThreadSafe
public class AtomicExample4 {
    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) {
        count.compareAndSet(0,2); // 2
        count.compareAndSet(0,1); // no
        count.compareAndSet(1,3); // no
        count.compareAndSet(2,4); // 4
        count.compareAndSet(3,5); // no
        log.info("count:{}", count.get());
    }
}
