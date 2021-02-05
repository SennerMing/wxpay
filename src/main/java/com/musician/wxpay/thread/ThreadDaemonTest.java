package com.musician.wxpay.thread;

/**
 * @author: LXR
 * @since: 2021/2/3 16:30
 */
public class ThreadDaemonTest {
    public static void main(String[] args) {
        ThreadDaemonA threadDaemonA = new ThreadDaemonA();
        ThreadDaemonB threadDaemonB = new ThreadDaemonB();
        threadDaemonA.setDaemon(true);

        threadDaemonB.start();
        threadDaemonA.start();
        Thread mainThread = Thread.currentThread();

        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();

        System.out.println("当前线程所在线程组的活动数量："+threadGroup.activeCount());

        System.out.println("线程A是不是守护线程：" + threadDaemonA.isDaemon());
        System.out.println("线程B是不是守护线程：" + threadDaemonB.isDaemon());
        System.out.println("线程main是不是守护线程：" + mainThread.isDaemon());
    }
}
