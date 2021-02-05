package com.musician.wxpay.thread;

import java.util.concurrent.ExecutionException;

/**
 * @author: LXR
 * @since: 2021/2/3 14:36
 */
public class ThreadInterruptDemo implements Runnable {
    @Override
    public void run() {
        boolean stop = false;
        while (!stop) {
            System.out.println("My Thread is running...");
            long time = System.currentTimeMillis();
            while((System.currentTimeMillis()-time < 1000)){

            }
            if (Thread.currentThread().isInterrupted()) {
//                Thread.currentThread();
                break;
            }
        }
        System.out.println("My Thread exiting under request...");
    }

    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new ThreadInterruptDemo(), "InterruptDemo Thread");
        System.out.println("Starting thread");
        thread.start();
        Thread.sleep(3000);
        System.out.println("Interrupting thread...");
        thread.interrupt();
        System.out.println("线程是都中断：" + thread.isInterrupted());
        Thread.sleep(3000);
        System.out.println("Stopping application...");

    }
}
