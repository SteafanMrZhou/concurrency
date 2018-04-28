package com.ymall.concurrency.commonUnsafe;

import com.ymall.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * StringBuilder->线程不安全
 * Created by Steafan on 2018/4/28 0028.
 */
@Slf4j
@NotThreadSafe
public class StringExample1 {
    private static int clientTotal = 5000;
    private static int threadTotal = 200;

    public static StringBuilder stringBuilder = new StringBuilder();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for(int i = 0; i < clientTotal; i++){
            service.execute(() -> {
                try{
                    semaphore.acquire();
                    update();
                    semaphore.release();
                }catch (Exception e){
                    log.info("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        service.shutdown();
        log.info("size{}", stringBuilder.length());
    }

    private static void update(){
        stringBuilder.append("1");
    }
}
