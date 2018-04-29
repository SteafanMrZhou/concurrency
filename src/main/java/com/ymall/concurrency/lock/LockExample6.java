package com.ymall.concurrency.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class LockExample6 {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        new Thread(() -> {
            try{
                // T1获取锁，即将T1放入AQS等待队列中
                reentrantLock.lock();
                log.info("wait signal");
                // 调用如下condition方法会将存在于AQS等待队列中的T1取出，放入condition的等待队列中
                // 这个过程相当于AQS释放了锁，所以T2捕获了锁转而去T2执行相应的操作
                condition.await();
            }catch (Exception e){
                e.printStackTrace();
            }
            // T1被唤醒之后执行log操作
            log.info("get signal");
            // T1释放锁，整个过程执行结束
            reentrantLock.unlock();
        }).start();

        new Thread(() -> {
            reentrantLock.lock();
            log.info("get lock");
            try{
                Thread.sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
            }
            condition.signalAll();
            // 此时，存在于condition等待队列中的T1将被再次放入AQS等待队列中，
            // 但是此时的T1并没有被唤醒，只是将T1从condition等待队列中取出而已
            log.info("send signal");
            // 在T2执行完unLock方法之后，在AQS队列中的T1被唤醒
            reentrantLock.unlock();
        }).start();
    }

}
