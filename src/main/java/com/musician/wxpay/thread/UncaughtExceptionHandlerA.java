package com.musician.wxpay.thread;

/**
 * @author: LXR
 * @since: 2021/2/4 11:31
 */
public class UncaughtExceptionHandlerA implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("An Exception has been captured\n");
        System.out.printf("Thread:%s\n", t.getId());
        System.out.printf("Exception:%s:%s\n", e.getClass().getName(), e.getMessage());
        System.out.printf("Stack Trace:\n");
        e.printStackTrace();
        System.out.printf("Thread status:%s\n", t.getState());
    }
}
