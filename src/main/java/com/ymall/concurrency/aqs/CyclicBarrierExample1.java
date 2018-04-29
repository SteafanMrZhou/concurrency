package com.ymall.concurrency.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Steafan on 2018/4/28 0028.
 */
@Slf4j
public class CyclicBarrierExample1 {
    // 可以优先执行lambda表达式中的内容，优先执行runnable
    private static CyclicBarrier barrier = new CyclicBarrier(5, () -> {
        log.info("callback is running");
    });

    public static void main(String[] args) throws Exception{
        ExecutorService service = Executors.newCachedThreadPool();

        for(int i = 0; i < 10; i++){
            final int threadNum = i;
            Thread.sleep(1000);
            service.execute(() -> {
                try{
                    race(threadNum);
                }catch (Exception e){
                    log.error("exception", e);
                }
            });
        }
        service.shutdown();
    }

    private static void race(int threadNum) throws Exception{
        Thread.sleep(1000);
        log.info("{} is ready", threadNum);
        // 允许放入等待时间，注意捕获异常
        barrier.await();
        log.info("{} continue", threadNum);
    }
}
