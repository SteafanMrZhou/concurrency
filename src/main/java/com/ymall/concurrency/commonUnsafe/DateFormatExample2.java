package com.ymall.concurrency.commonUnsafe;

import com.ymall.concurrency.annotations.NotThreadSafe;
import com.ymall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * SimpleDateFormat->线程不安全
 * 解决方法：将SimpleDateFormat声明为局部变量使用
 * 这样是线程封闭->堆栈封闭实现，保证线程安全
 * Created by Steafan on 2018/4/28 0028.
 */
@Slf4j
@ThreadSafe
public class DateFormatExample2 {


    private static int clientTotal = 5000;
    private static int threadTotal = 200;

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
    }

    private static void update(){
        try{
            // 将SimpleDateFormat声明为局部变量
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            simpleDateFormat.parse("20180428");
        }catch (Exception e){
            log.error("parse exception", e);
        }
    }
}
