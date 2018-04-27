package com.ymall.concurrency.singleton;


import com.ymall.concurrency.annotations.Recommend;
import com.ymall.concurrency.annotations.ThreadSafe;

/**
 * 枚举模式 更安全 初始化较好
 * Created by Steafan on 2018/4/27 0027.
 */
@ThreadSafe
@Recommend
public class SingletonExample7 {
    private SingletonExample7(){

    }

    public static SingletonExample7 getInstance(){
        return Singleton.INSTANCE.getSingleton();
    }

    private enum Singleton{
        INSTANCE;

        private SingletonExample7 singleton;

        // JVM保证这个方法绝对只调用一次
        Singleton(){
            singleton = new SingletonExample7();
        }

        public SingletonExample7 getSingleton() {
            return singleton;
        }
    }
}
