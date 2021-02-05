package com.musician.wxpay.thread.safety;

/**
 * @author: LXR
 * @since: 2021/2/4 13:48
 */
public class ThreadA extends Thread{
    private Count count;

    public ThreadA(Count count) {
        this.count = count;
    }

    public void run() {
        count.add();
    }
}
