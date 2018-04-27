package com.ymall.concurrency.singleton;

import com.ymall.concurrency.annotations.NotThreadSafe;

/**
 * 懒汉模式
 * 单例实例在第一次使用时创建
 * Created by Steafan on 2018/4/27 0027.
 */
@NotThreadSafe
public class SingletonExample1 {
    // 私有构造器，保证不能让其范围之外的类随意生成对象
    private SingletonExample1(){

    }
    // 单例的对象
    private static SingletonExample1 instance = null;

    // 静态的工程方法
    public static SingletonExample1 getInstance(){
        if(instance == null){
            instance = new SingletonExample1();
        }
        return instance;
    }
}
