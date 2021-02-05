package com.musician.wxpay.thread.safety;

/**
 * @author: LXR
 * @since: 2021/2/4 13:49
 */
public class ThreadCountTest {
    public static void main(String[] args) {
        Count count = new Count();
        for (int i = 0; i < 5; i++) {
            ThreadA threadA = new ThreadA(count);
            threadA.start();
        }

        try {
            Thread.sleep(100l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("5个人干完活：最后的值" + count.num);
    }
}
