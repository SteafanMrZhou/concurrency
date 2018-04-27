package com.ymall.concurrency.singleton;


/**
 * 饿汉模式
 * Created by Steafan on 2018/4/27 0027.
 */
public class SingletonExample6 {
    private SingletonExample6(){

    }

    // 注意静态代码块的代码顺序
    private static SingletonExample6 instance = null;

    static {
        instance = new SingletonExample6();
    }

    public static SingletonExample6 getInstance(){
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(getInstance().hashCode());
        System.out.println(getInstance().hashCode());
    }
}
