package com.ymall.concurrency.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by Steafan on 2018/4/28 0028.
 */
@Slf4j
public class SemaphoreExample1 {
    private static final int threadCount = 20;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();

        // 控制同一时间可执行的最大线程数-3个(每一秒最多执行3个线程)
        final Semaphore semaphore = new Semaphore(3);

        for(int i = 0; i < threadCount; i++){
            final int threadNum = i;
            service.execute(() -> {
                try{
                    // 获取一个许可
                    semaphore.acquire();
                    test(threadNum);
                    // 释放一个许可
                    semaphore.release();
                }catch (Exception e){
                    log.error("exception", e);
                }
            });
        }
        log.info("finish");
        service.shutdown();
    }

    private static void test(int threadNum) throws Exception{
        Thread.sleep(100);
        log.info("{}", threadNum);
        Thread.sleep(100);
    }
}
