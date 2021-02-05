package com.musician.wxpay.thread;

import java.util.concurrent.Callable;

/**
 * @author: LXR
 * @since: 2021/2/3 11:21
 */
public class ThreadC implements Callable<String> {
    @Override
    public String call() throws Exception {
        try {
            Thread.sleep(500);
            System.out.println("线程C中："+Thread.currentThread());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("这是线程C");
        return "Thread C";
    }
}
