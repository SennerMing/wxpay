package com.musician.wxpay.thread.safety;

/**
 * @author: LXR
 * @since: 2021/2/6 8:55
 */
public class RWCountTest {
    public static void main(String[] args) {
        final RWCount rwCount = new RWCount();
        for(int i = 0;i < 2;i++){
            new Thread(){
                public void run() {
                    rwCount.get();
                }
            }.start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(){
                public void run() {
                    rwCount.put();
                }
            }.start();
        }
    }
}
