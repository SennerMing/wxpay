package com.musician.wxpay.thread;

/**
 * @author: LXR
 * @since: 2021/2/3 16:26
 */
public class ThreadDaemonB extends Thread{
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("线程B第" + i + "次执行");
            try {
                Thread.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
