package com.ymall.concurrency.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Steafan on 2018/4/27 0027.
 */
@Slf4j
public class SyncronizedExample1 {

    // 修饰一个代码块
    public void test1(){
        synchronized(this){
            for(int i = 0; i < 10; i++){
                log.info("test1-{}", i);
            }
        }
    }

    // 修饰一个方法
    public synchronized void test2(){
        for(int i = 0; i < 10; i++){
            log.info("test2-{}", i);
        }
    }

    public static void main(String[] args) {
        SyncronizedExample1 example1 = new SyncronizedExample1();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            example1.test1();
        });
        executorService.execute(() -> {
            example1.test1();
        });
    }
}
