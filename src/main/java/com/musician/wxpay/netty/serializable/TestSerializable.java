package com.musician.wxpay.netty.serializable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * @author dingyuxiang
 * @date 2021-03-02 13:53
 */
public class TestSerializable {
    public static void main(String[] args) throws IOException {

//        UserInfo userInfo = new UserInfo();
//        userInfo.buildUserId(100).buildUserName("Welcome to Netty");
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//        objectOutputStream.writeObject(userInfo);
//        objectOutputStream.flush();
//        objectOutputStream.close();
//        byte[] b = byteArrayOutputStream.toByteArray();
//        System.out.println("The JDK serializable length is:"+b.length);
//        byteArrayOutputStream.close();
//        System.out.println("==================================================");
//        System.out.println("The byte array serializable length is:"+userInfo.codeC().length);



        UserInfo userInfo = new UserInfo();
        userInfo.buildUserId(100).buildUserName("Welcome to Netty");
        int loop = 1000000;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        long startTime = System.currentTimeMillis();
        for(int i = 0;i < loop;i++){
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(userInfo);
            os.flush();
            os.close();
            byte[] b = bos.toByteArray();
            bos.close();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The jdk serializable cost time is:"+(endTime-startTime)+" ms");
        System.out.println("==============================================================");
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        startTime = System.currentTimeMillis();
        for(int i = 0;i < loop;i++){
            byte[] b = userInfo.codeC();
        }
        endTime = System.currentTimeMillis();
        System.out.println("The byte array serializable cost time is:"+(endTime-startTime)+" ms");



    }
}
