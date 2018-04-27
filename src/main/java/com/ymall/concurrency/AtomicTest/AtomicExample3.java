package com.ymall.concurrency.AtomicTest;

import com.ymall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder-JDK1.8中新增的特性
 * 线程安全-LongAdder(优势在于当线程竞争激烈的时候其会进行分散ceil处理)
 * 所在在线程竞争激烈（高并发）的情况下使用LongAdder而不是用AtomicLong
 * Created by Steafan on 2018/4/27 0027.
 */
@Slf4j
@ThreadSafe
public class AtomicExample3 {
    private static int clientTotal = 5000;
    private static int threadTotal = 200;

    private static LongAdder count = new LongAdder();

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
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();
        log.info("count:{}", count);
    }

    private static void add(){
        count.increment();
    }
}
