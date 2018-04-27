package com.ymall.concurrency.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Steafan on 2018/4/27 0027.
 */
@Slf4j
public class SyncronizedExample2 {

    // 修饰一个类
    public static void test1(int j){
        synchronized(SyncronizedExample2.class){
            for(int i = 0; i < 10; i++){
                log.info("test1 {} - {}", i, j);
            }
        }
    }

    // 修饰一个方法
    public static synchronized void test2(int j){
        for(int i = 0; i < 10; i++){
            log.info("test2 {} - {}", i, j);
        }
    }

    public static void main(String[] args) {
        SyncronizedExample2 example1 = new SyncronizedExample2();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            example1.test1(1);
        });
        executorService.execute(() -> {
            example1.test1(2);
        });
    }
}
