package com.musician.wxpay.thread;

/**
 * @author: LXR
 * @since: 2021/2/4 10:38
 */
public class ThreadLocalTest {
    private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>(){
        public Integer initialValue() {
            return 0;
        }
    };

    public ThreadLocal<Integer> getThreadLocal() {
        return seqNum;
    }

    public int getNextNum() {
        seqNum.set(seqNum.get() + 1);
        return seqNum.get();
    }

    private static class TestClient extends Thread{

        private ThreadLocalTest threadLocalTest;

        public TestClient(ThreadLocalTest threadLocalTest) {
            this.threadLocalTest = threadLocalTest;
        }

        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println("Thread[" + Thread.currentThread().getName() + "]" +
                        " --> threadLocalTest.getNextNum:" + threadLocalTest.getNextNum()+"");
            }
            threadLocalTest.getThreadLocal().remove();
        }

    }


    public static void main(String[] args) {
        ThreadLocalTest tm = new ThreadLocalTest();
        TestClient t1 = new TestClient(tm);
        TestClient t2 = new TestClient(tm);
        TestClient t3 = new TestClient(tm);
        t1.start();
        t2.start();
        t3.start();
    }

}

