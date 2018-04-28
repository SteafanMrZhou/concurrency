package com.ymall.concurrency.concurrent;

import com.ymall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

/**
 * CopyOnWriteArrayList-J.U.C
 * 适合多读少写的场景
 * 当有新数据add到list中时，也就是当CopyOnWriteArrayList在进行写操作时，其会复制
 * 一个新数组出来，在新数组中进行元素的add操作，待该操作执行完成后，再指向旧数组中元素的位置
 * 读操作时不加锁，写操作时加锁
 * 实现原理：读写分离、保持最终结果一致，在进行相应操作时开辟新的内存空间
 * Created by Steafan on 2018/4/28 0028.
 */
@Slf4j
@ThreadSafe
public class CopyOnWriteArrayListExample {
    private static int clientTotal = 5000;
    private static int threadTotal = 200;

    private static List<Integer> list = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for(int i = 0; i < clientTotal; i++){
            try {
                final int count = i;
                semaphore.acquire();
                update(count);
                semaphore.release();
            }catch (Exception e){
                log.error("exception", e);
            }
            countDownLatch.countDown();
        }
        countDownLatch.await();
        service.shutdown();
        log.info("size:{}", list.size());
    }

    private static void update(int i){
        list.add(i);
    }
}
