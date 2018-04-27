package com.ymall.concurrency.singleton;

import com.ymall.concurrency.annotations.NotThreadSafe;

/**
 * 线程不安全的，在new一个对象时在多线程环境下，
 * 会发生指令重排序问题，打乱原有的生成对象的顺序
 * 1、在堆中为对象分配对象的内存空间
 * 2、在堆中初始化对象
 * 3、设置new出的对象指向刚分配的内存空间
 * 在多线程情况下，发生指令重排序时会打乱1232顺序，可能变为132这种顺序
 * 导致程序运行不能达到预期效果
 * Created by Steafan on 2018/4/27 0027.
 */
@NotThreadSafe
public class SingletonExample4 {
    private SingletonExample4(){

    }
    private static SingletonExample4 instance = null;

    // 双重锁机制
    public static SingletonExample4 getInstance(){
        if(instance == null){
            // 同步锁
            synchronized (SingletonExample4.class){
                if(instance == null){
                    instance = new SingletonExample4();
                }
            }
        }
        return instance;
    }
}
