package com.ymall.concurrency.commonUnsafe;

import com.ymall.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * HashSet在不声明为局部变量时是线程不安全的
 * Created by Steafan on 2018/4/28 0028.
 */
@Slf4j
@NotThreadSafe
public class HashSetExample {
    private static int clientTotal = 5000;
    private static int threadTotal = 200;

    private static Set<Integer> set = new HashSet<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for(int i = 0; i < clientTotal; i++){
            final int count = i;
            service.execute(() -> {
                try {
                    semaphore.acquire();
                    update(count);
                    semaphore.release();
                }catch (Exception e){
                    log.info("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        service.shutdown();
        log.info("size:{}", set.size());
    }

    private static void update(int i){
        set.add(i);
    }
}
