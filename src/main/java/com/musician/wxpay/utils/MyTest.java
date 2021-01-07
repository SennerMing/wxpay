package com.musician.wxpay.utils;

/**
 * @author: LXR
 * @since: 2021/1/5 16:14
 */
public class MyTest {
    public void test(GrandPa grandPa){
        System.out.println("grandpa");
    }
    public void test(Father father){
        System.out.println("father");
    }
    public void test(Son son){
        System.out.println("son");
    }

    public static void main(String[] args) {
//        GrandPa father = new Father();
//        GrandPa son = new Son();
//        MyTest myTest = new MyTest();
//        myTest.test(father);
//        myTest.test(son);

        String classPath = System.getProperty("java.class.path");
        for (String path : classPath.split(";")) {
            System.out.println(path);
        }
    }

}

class GrandPa{

}
class Father extends GrandPa{

}
class Son extends Father{

}
