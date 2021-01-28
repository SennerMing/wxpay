package com.musician.wxpay.utils;

/**
 *
 * @author: LXR
 * @since: 2021/1/27 9:27
 */
public class Initialization {

    public static String a = "123456";

    public static Integer b = 123456;

    public static int c = 123;

    public static final String d = new String("133");

    public static final Integer e = 22233;

    public static final int f = 12314535;

    public static final String g = "789";

    static {
        System.out.println("Initialization 初始化了");
    }

    public static void main(String[] args) {
        System.out.println(Initialization.g);
        System.out.println(Initialization.f);
    }

}
