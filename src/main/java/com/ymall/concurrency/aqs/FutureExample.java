package com.ymall.concurrency.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Steafan on 2018/4/29 0029.
 */
@Slf4j
public class FutureExample {
    static  class MyCallable implements Callable<String>{
        @Override
        public String call() throws Exception {
            log.info("do something in callable");
            Thread.sleep(5000);
            return "done";

        }
    }

    public static void main(String[] args) throws Exception{
        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(new MyCallable());
        log.info("do something in main");
        Thread.sleep(1000);
        String result = future.get();
        log.info("result: {}", result);
    }
}
