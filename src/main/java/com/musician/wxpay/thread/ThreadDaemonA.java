package com.musician.wxpay.thread;

/**
 * @author: LXR
 * @since: 2021/2/3 16:26
 */
public class ThreadDaemonA extends Thread {
    public void run() {
        for (long i = 0; i < 999999999l; i++) {
            System.out.println("后台线程A第" + i + "次执行");
            try {
                Thread.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
