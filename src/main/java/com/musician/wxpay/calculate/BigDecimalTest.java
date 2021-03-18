package com.musician.wxpay.calculate;

public class BigDecimalTest {


    public static void main(String[] args) {
//        float f = 20014999;
//        double d = f;
//        double d2 = 20014999;
//        System.out.println("f=" + f);
//        System.out.println("d=" + d);
//        System.out.println("d2=" + d2);


        double d = 8;
        long l = Double.doubleToLongBits(d);
        System.out.println(Long.toBinaryString(l));
        float f = 8;
        int i = Float.floatToIntBits(f);
        System.out.println(Integer.toBinaryString(i));


    }


}
