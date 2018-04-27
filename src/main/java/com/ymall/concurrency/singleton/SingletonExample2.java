package com.ymall.concurrency.singleton;

import com.ymall.concurrency.annotations.ThreadSafe;

/**
 * 饿汉模式
 * 单例实例在类装载时进行创建
 * 使用前提：
 * 在私有构造方法中没有过多的逻辑处理；
 * 保证使用饿汉模式的类肯定会被使用，不会对资源造成浪费
 * Created by Steafan on 2018/4/27 0027.
 */
@ThreadSafe
public class SingletonExample2 {
    private SingletonExample2(){

    }
    private static SingletonExample2 instance = new SingletonExample2();

    public static SingletonExample2 getInstance(){
        return instance;
    }
}
