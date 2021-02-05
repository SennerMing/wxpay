package com.musician.wxpay.thread;

/**
 * @author: LXR
 * @since: 2021/2/3 13:59
 */
public class ThreadB implements Runnable {
    @Override
    public void run() {

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread curThread = Thread.currentThread();
        String curThreadName = curThread.getName();
        System.out.println("线程名称：" + curThread.getName());
        System.out.println("当前线程：" + curThreadName + "的线程组中活动线程的数目：" + Thread.activeCount());
        System.out.println("当前线程：" + curThreadName + "的标识符：" + curThread.getId());
        System.out.println("当前线程：" + curThreadName + "的状态：" + curThread.getState());
        System.out.println("当前线程：" + curThreadName + "所属的线程组：" + curThread.getThreadGroup());
        System.out.println("当前线程：" + curThreadName + "是否处于活动状态：" + curThread.isAlive());
        System.out.println("当前线程：" + curThreadName + "是否测试该线程为守护线程：" + curThread.isDaemon());
    }
}
