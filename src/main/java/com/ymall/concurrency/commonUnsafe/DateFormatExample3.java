package com.ymall.concurrency.commonUnsafe;

import com.ymall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
public class DateFormatExample3 {


    private static int clientTotal = 5000;
    private static int threadTotal = 200;

    private static DateTimeFormatter dateTimeFormatter =
            DateTimeFormat.forPattern("yyyyMMdd");


    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for(int i = 0; i < clientTotal; i++){
            final int count = i;
            service.execute(() -> {
                try{
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
    }

    private static void update(int i){
        log.info("{},{}", i, DateTime.parse("20180428", dateTimeFormatter).toDate());
    }
}
