package com.ymall.concurrency.publish;

import com.ymall.concurrency.annotations.NotRecommend;
import com.ymall.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Steafan on 2018/4/27 0027.
 */
@Slf4j
@NotThreadSafe
@NotRecommend
public class Escape {
    private int thisCanBeEscape = 0;
    public Escape(){
        new InnerClazz();
    }

    private class InnerClazz{
        public InnerClazz(){
            log.info("{}", Escape.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args) {
        new Escape();
    }
}
