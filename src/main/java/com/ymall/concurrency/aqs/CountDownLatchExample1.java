package com.ymall.concurrency.aqs;

import com.ymall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Steafan on 2018/4/28 0028.
 */
@Slf4j
public class CountDownLatchExample1 {
    private static final int threadCount = 200;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for(int i = 0; i < threadCount; i++){
            final int threadNum = i;
            service.execute(() -> {
                try{
                    test(threadNum);
                }catch (Exception e){
                    log.error("exception", e);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        log.info("finish");
        service.shutdown();
    }

    private static void test(int threadNum) throws Exception{
        Thread.sleep(100);
        log.info("{}", threadNum);
        Thread.sleep(100);
    }
}
