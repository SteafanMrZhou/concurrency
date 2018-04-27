package com.ymall.concurrency.singleton;

import com.ymall.concurrency.annotations.NotRecommend;
import com.ymall.concurrency.annotations.NotThreadSafe;
import com.ymall.concurrency.annotations.ThreadSafe;

/**
 * Created by Steafan on 2018/4/27 0027.
 */
@ThreadSafe
@NotRecommend
public class SingletonExample3 {
    private SingletonExample3(){

    }
    private static SingletonExample3 instance = null;

    // 保证了方法的原子性，同一时间只允许一个线程访问，也带来了性能上的问题，不推荐使用
    public static synchronized SingletonExample3 getInstance(){
        if(instance == null){
            instance = new SingletonExample3();
        }
        return instance;
    }
}
