package com.musician.wxpay.symmetric;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


/**
 * @ClassName: Aes
 * @Description:
 * =================================================================================================
 *      Symmetric encryption（对称加密），就是传统的用一个密码进行加密和解密。
 *      例如，我们常用的压缩文件工具对压缩包进行加解密，就是使用的Symmetric encryption
 *      1.encrypt 接收[密码]和[明文]，输出[密文]
 *      2.decrypt 接收[密码]和[密文]，输出[明文]
 *      常用的对称加密算法有：
 *       |   名称    |  密钥长度    |        工作作模式           |
 *       |   DES    |    56/64    |   ECB/CBC/PCBC/CTR/...    |
 *       |   AES    | 128/192/256 |   ECB/CBC/PCBC/CTR/...    |
 *       |   IDEA   |     128     |         ECB               |
 *       ** ECB：Electronic CodeBook(电码本)，是分组密码的一种最基本的工作模式，将待处理的信息被分为大小合适的分组，
 *              然后分别对每一组进行独立的加解密处理。
 *              优点：操作简单，易于实现。分组具有独立性，利于并行处理，并且能很好的防止误差传播。
 *              缺点：所有分组加密方式一致，铭文中重复内容会在密文中有所体现，因此难以抵抗统计分析攻击。
 *              适用场景：适用于小数据量的字符信息安全性保护，例如密钥保护。
 *       ** CBC：Cipher Block Chaining（密文分组链接模式），ECB模式只进行了加密处理，而CBC模式在加密之前一定会
 *              与”前一个密文分组“进行了一次XOR运算，因此弥补了ECB难以抵抗统计分析攻击的缺陷。
 *              1.使用时需要指定初始化向量Initailization Vector
 *              2.互联网安全通信协议之一的SSL/TLS，就是使用CBC模式确保通信机密性的，有会遭受”填充攻击“的说法（在密钥相同的情况下）。
 *      DES算法生成的密钥长度过短，可在短时间内暴力破解，已经不太安全了。
 * =================================================================================================
 * AES算法是目前应用最广泛的加密算法。本文以ECB及CBC模式进行加密解密。
 *
 * 加解密过程总结：
 *      1.根据算法名称/工作模式/填充模式获取Cipher实例；
 *      2.根据算法名称初始化一个SecretKey实例，密钥必须是指定长度
 *      3.使用SecretKey初始化Cipher实例，并设置加密或解密模式【加解密的主要操作】
 *      4.传入明文或密文，获得密文或明文
 *
 * 对称加密算法使用同一个密钥进行加密和解密，常用算法有DES、AES和IDEA等；
 * 密钥长度由算法设计决定，AES的密钥长度是128/192/256位；
 * 使用对称加密算法需要指定算法名称、工作模式和填充模式。
 *
 * @Author: LXR
 * @DATE: 2020/12/29 13:21
 * @Version: 1.0
 */
public class Aes {

    public static void main(String[] args) {
        try {
            //==============================================EBC模式加密===================================================
            //输入一段原文
            String message_ecb = "HelloWorld";
            //128位密钥 = 16字节的key [一字节八比特]
//            byte[] key = "1234567890abcdef".getBytes("UTF-8");
            //192位密钥 = 24字节的key [一字节八比特]
            byte[] key_ecb = "1234567890abcdefghijklmn".getBytes("UTF-8");
            //进行加密处理：
            byte[] data_ecb = message_ecb.getBytes("UTF-8");
            byte[] encrypted_ecb = encryptEBC(key_ecb, data_ecb);
            System.out.println("Enctypted_EBC:"+ Base64.getEncoder().encodeToString(encrypted_ecb));

            //==============================================EBC模式解密===================================================
            byte[] decrypted_ecb = decryptEBC(key_ecb, encrypted_ecb);
            System.out.println("Decrypted_EBC: " + new String(decrypted_ecb, "UTF-8"));


            //==============================================CBC模式加密===================================================
            //原文
            String message_cbc = "HelloWorld";
            //256位密钥 = 32字节key
            byte[] key_cbc = "1234567890abcdef1234567890abcdef".getBytes("UTF-8");
            //加密
            byte[] data_cbc = message_cbc.getBytes("UTF-8");
            byte[] encrypted_cbc = encryptCBC(key_cbc, data_cbc);
            System.out.println("Encrypted_CBC: " + Base64.getEncoder().encodeToString(encrypted_cbc));

            //==============================================CBC模式解密===================================================
            byte[] decrypted_cbc = decryptCBC(key_cbc, encrypted_cbc);
            System.out.println("Decrypted_EBC："+new String(decrypted_cbc,"UTF-8"));



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * AES CBC模式下解密
     * @param key_cbc
     * @param encrypted_cbc
     * @return
     */
    private static byte[] decryptCBC(byte[] key_cbc, byte[] encrypted_cbc) {
        //将IV（初始化向量）与密文进行分割
        byte[] iv = new byte[16];
        byte[] data = new byte[encrypted_cbc.length - 16];
        System.arraycopy(encrypted_cbc, 0, iv, 0, 16);
        System.arraycopy(encrypted_cbc, 16, data, 0, data.length);
        //进行解密操作
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(key_cbc, "AES");
            IvParameterSpec ivps = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivps);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES CBC模式加密
     * @param key_cbc
     * @param data_cbc
     * @return
     */
    public static byte[] encryptCBC(byte[] key_cbc, byte[] data_cbc) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(key_cbc, "AES");
            // CBC 模式需要生成一个16字节的initialization vectory初始化向量，第一个分组需要根据他生成密文，作为第二个加解密的key输入
            // SecureRandom与Random相比是密码安全的随机数，其使用操作系统收集的一些随机事件作为种子，
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            byte[] iv = secureRandom.generateSeed(16);
            System.out.println("IV:"+new BigInteger(1,iv).toString(16));
            IvParameterSpec ivps = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivps);
            byte[] data = cipher.doFinal(data_cbc);
            return join(iv, data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将两个byte数组合并
     * @param bs1
     * @param bs2
     * @return
     */
    public static byte[] join(byte[] bs1, byte[] bs2) {
        byte[] r = new byte[bs1.length + bs2.length];
        System.arraycopy(bs1, 0, r, 0, bs1.length);
        System.arraycopy(bs2, 0, r, bs1.length, bs2.length);
        return r;
    }

    /**
     * 加密
     * @param key_ebc
     * @param input
     * @return
     */
    public static byte[] encryptEBC(byte[] key_ebc,byte[] input){

        try {
            //获得密文实例
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //将128位密钥生成对应的AES密钥[DES与IDEA可以作为AES算法的代替]
            SecretKey secretKey = new SecretKeySpec(key_ebc, "AES");
            //填充根据明文密钥生成的AES密钥
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            //将AES密钥与消息进行加密处理
            return cipher.doFinal(input);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param key_ebc
     * @param input
     * @return
     */
    public static byte[] decryptEBC(byte[] key_ebc,byte[] input){
        try {
            //获得密文实例指定 算法、工作模式
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //通过明文密钥创建AES算法实例
            SecretKey secretKey = new SecretKeySpec(key_ebc, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(input);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

}
