package com.musician.wxpay.thread.safety;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: LXR
 * @since: 2021/2/4 16:18
 */
public class ReentrantLockDemo {
    public static void main(String[] args) {
        final ReentrantLockCount count = new ReentrantLockCount();
        for (int i = 0; i < 2; i++) {
            System.out.println("第"+i+"get()");
            new Thread() {
                public void run() {
                    count.get();
                }
            }.start();
        }

        for (int i = 0; i < 2; i++) {
            System.out.println("第"+i+"put()");
            new Thread(){
                public void run() {
                    count.put();
                }
            }.start();
        }
    }
}

class ReentrantLockCount{
//    public void get() {
//        final ReentrantLock reentrantLock = new ReentrantLock();
//        try {
//            reentrantLock.lock();
//            System.out.println(Thread.currentThread().getName() + " get begin!");
//            Thread.sleep(1000L);
//            System.out.println(Thread.currentThread().getName() + " get end!");
//            reentrantLock.unlock();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void put() {
//        final ReentrantLock reentrantLock = new ReentrantLock();
//        try {
//            reentrantLock.lock();
//            System.out.println(Thread.currentThread().getName() + " get begin!");
//            Thread.sleep(1000l);
//            System.out.println(Thread.currentThread().getName() + " get end!");
//            reentrantLock.unlock();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    final ReentrantLock reentrantLock = new ReentrantLock();

    public void get() {
        try {
            reentrantLock.lock();
            System.out.println(Thread.currentThread().getName() + " get begin!");
            Thread.sleep(1000l);
            System.out.println(Thread.currentThread().getName() + " get end!");
            reentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void put() {
        try {
            reentrantLock.lock();
            System.out.println(Thread.currentThread().getName() + " put begin!");
            Thread.sleep(1000l);
            System.out.println(Thread.currentThread().getName() + " put end!");
            reentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}