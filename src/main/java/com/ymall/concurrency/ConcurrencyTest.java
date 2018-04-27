package com.ymall.concurrency;

import com.ymall.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 用代码进行并发测试/模拟
 * Created by Steafan on 2018/4/27 0027.
 */
@Slf4j
@NotThreadSafe
public class ConcurrencyTest {
    private static int clientTotal = 5000;
    private static int threadTotal = 200;

    public static long count = 0;

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for(int i = 0; i < clientTotal; i++){
            service.execute(() -> {
                try{
                    semaphore.acquire();
                    add();
                    semaphore.release();
                }catch (Exception e){
                    log.error("exception:", e);
                }
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("InterruptedException:", e);
        }
        service.shutdown();
        log.info("count:{}", count);
    }

    private static void add(){
        count++;
    }
}
