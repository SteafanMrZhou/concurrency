package com.ymall.concurrency.AtomicTest;

import com.ymall.concurrency.annotations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * AtomicIntegerFieldUpdater<T> updater
 * updater.compareAndSet(expect, update)
 * ->将对象的实例进行更新
 * Created by Steafan on 2018/4/27 0027.
 */
@Slf4j
@ThreadSafe
public class AtomicExample5 {
    private static AtomicIntegerFieldUpdater<AtomicExample5> updater =
            AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class, "count");


    @Getter
    public volatile int count = 100;

    private static AtomicExample5 example5 = new AtomicExample5();

    public static void main(String[] args) {
        if(updater.compareAndSet(example5, 100, 120)){
            log.info("update success1 {}", example5.getCount());
        }

        if(updater.compareAndSet(example5, 100, 120)){
            log.info("update success {}", example5.getCount());
        }else{
            log.info("update failed {}", example5.getCount());
        }
    }
}
