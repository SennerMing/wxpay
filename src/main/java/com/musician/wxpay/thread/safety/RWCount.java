package com.musician.wxpay.thread.safety;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: LXR
 * @since: 2021/2/6 8:48
 */
public class RWCount {
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public void get() {
        reentrantReadWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "read start");
            Thread.sleep(1000L);
            System.out.println(Thread.currentThread().getName() + "read end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    public void put() {
        reentrantReadWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "write start");
            Thread.sleep(1000L);
            System.out.println(Thread.currentThread().getName() + "write end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

}
