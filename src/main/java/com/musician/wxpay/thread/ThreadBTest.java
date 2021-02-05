package com.musician.wxpay.thread;

/**
 * @author: LXR
 * @since: 2021/2/3 14:06
 */
public class ThreadBTest {
    public static void main(String[] args) {
        ThreadB threadB = new ThreadB();
        for (int i = 0; i < 5; i++) {
            new Thread(threadB, "线程名称：(" + i + ")").start();
        }
        Thread threadMain = Thread.currentThread();
        System.out.println("这是主线程");
        System.out.println("返回当前线程的线程组中活动的线程数目：" + Thread.activeCount());
        System.out.println("主线程名称是：" + threadMain.getName());
        System.out.println("主线程的标识符：" + threadMain.getId());
        System.out.println("主线程的优先级：" + threadMain.getPriority());
        System.out.println("主线程的状态" + threadMain.getState());
        System.out.println("主线程是否存活：" + threadMain.isAlive());

        try {
            Thread.sleep(10000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
