package com.musician.wxpay.thread;

import com.baomidou.mybatisplus.extension.api.R;

/**
 * @author: LXR
 * @since: 2021/2/4 11:30
 */
public class ThreadUncaughtExceptionHandlerTest {

    public static void main(String[] args) {
        ThreadUncaught threadUncaught = new ThreadUncaught();
        Thread thread = new Thread(threadUncaught);
        thread.setUncaughtExceptionHandler(
                new UncaughtExceptionHandlerA()
        );
        thread.start();

    }
}

class ThreadUncaught implements Runnable {
    public void run() {
        int number0 = Integer.parseInt("TTT");
    }
}
