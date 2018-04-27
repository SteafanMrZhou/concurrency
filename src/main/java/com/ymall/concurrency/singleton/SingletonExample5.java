package com.ymall.concurrency.singleton;

/**
 * 懒汉模式
 * 使用volatile+双重锁检测机制禁止指令重排序
 * Created by Steafan on 2018/4/27 0027.
 */
public class SingletonExample5 {
    private SingletonExample5(){

    }
    // volatile双重检测机制使用场景，注意，容易被忽略
    private volatile static SingletonExample5 instance = null;

    public static SingletonExample5 getInstance(){
        if(instance == null){
            synchronized(SingletonExample5.class){
                if(instance == null) {
                    instance = new SingletonExample5();
                }
            }
        }
        return instance;
    }
}
