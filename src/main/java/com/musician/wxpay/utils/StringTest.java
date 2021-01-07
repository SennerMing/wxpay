package com.musician.wxpay.utils;

/**
 * @author: LXR
 * @since: 2021/1/6 8:58
 */
public class StringTest {

    public static void main(String[] args) {
        String s1 = new String("123");;
        String inters1 = s1.intern();
        String pools1 = "123";

        System.out.println(s1 == inters1); //false
        System.out.println(pools1 == inters1); //true
        System.out.println(pools1 == s1); //false

        System.out.println("\n");

        String s2 = new String("Hel") + new String("lo");
        String interns2 = s2.intern();
        String pools2 = "Hello";
        System.out.println(s2 == interns2);
        System.out.println(pools2 == interns2);
        System.out.println(pools2 == s2);


    }



}
