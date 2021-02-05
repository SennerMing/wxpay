package com.musician.wxpay.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author: LXR
 * @since: 2021/2/3 11:23
 */
public class ThreadCTest {
    public static void main(String[] args) {
        ThreadC threadC = new ThreadC();
        FutureTask<String> futureTask = new FutureTask<>(threadC);
        new Thread(futureTask).start();
        System.out.println("这是主线程：begin！");
        try {
            System.out.println("得到的返回结果是：" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("主线程中："+Thread.currentThread());
        System.out.println("当前线程的线程组中活动线程的数目：" + Thread.activeCount());
        System.out.println("当前线程的堆栈跟中打印只标准错误流：");
        Thread.dumpStack();
        
        System.out.println("这是主线程：end!");

    }
}
