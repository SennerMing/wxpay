package com.musician.wxpay.thread.safety;

/**
 * @author: LXR
 * @since: 2021/2/4 13:44
 */
public class Count {
    public int num = 0;

    public synchronized void add() {
        try {
            Thread.sleep(5l);
            //如果线程在阻塞情况下被打断，则线程会立刻被唤醒并且抛出InterruptedException
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num += 1;
        System.out.println(Thread.currentThread().getName() + "-" + num);
    }

}
