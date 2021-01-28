package com.musician.wxpay.utils;

/**
 * @author: LXR
 * @since: 2021/1/6 8:58
 */
public class StringTest {



    static{
        a = "654321";
    }

    private static String a = "12456";

    public static void main(String[] args) {

        System.out.println(a);

//        String s1 = new String("123");;
//        String inters1 = s1.intern();
//        String pools1 = "123";
//
//        System.out.println(s1 == inters1); //false
//        System.out.println(pools1 == inters1); //true
//        System.out.println(pools1 == s1); //false
//
//        System.out.println("\n");
//
//        String s2 = new String("Hel") + new String("lo");
//        String interns2 = s2.intern();
//        String pools2 = "Hello";
//        System.out.println(s2 == interns2);
//        System.out.println(pools2 == interns2);
//        System.out.println(pools2 == s2);


        String s1 = "a";
        String s0 = new String("a");
        s0 = s0.intern();

        System.out.println(s0 == s1);
        String s2 = "b";
        String s3 = "ab";
        String s4 = s1 + s2;
        System.out.println(s3 == s4);


    }



}
