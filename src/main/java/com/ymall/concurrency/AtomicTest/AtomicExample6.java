package com.ymall.concurrency.AtomicTest;

import com.ymall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * AtomicBoolean isHappened
 * isHappened.compareAndSet(except, update)
 * isHappened.compareAndSet(false, true)
 * -> 保证代码只执行一次，不会重复执行
 * 当项目中部分代码需要在并发环境下只执行一次时，考虑使用AtomicBoolean
 * Created by Steafan on 2018/4/27 0027.
 */
@Slf4j
@ThreadSafe
public class AtomicExample6 {
    private static AtomicBoolean isHappend = new AtomicBoolean(false);

    public static int clientTotal = 5000;

    public static int threadTotal = 200;

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch downLatch = new CountDownLatch(clientTotal);
        for(int i = 0; i < clientTotal; i++){
            service.execute(() -> {
                try{
                    semaphore.acquire();
                    test();
                    semaphore.release();
                }catch (Exception e){
                    log.error("exception", e);
                }
                downLatch.countDown();
            });
        }
        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();
        log.info("isHappend:{}", isHappend.get());
    }

    private static void test(){
        // 只让一段代码执行一遍，不重复执行
        if(isHappend.compareAndSet(false,true)){
            log.info("execute");
        }
    }
}
