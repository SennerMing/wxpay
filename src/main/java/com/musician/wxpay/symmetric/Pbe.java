package com.musician.wxpay.symmetric;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * @ClassName: Peb
 * @Description:
 *    对于对称加密算法中的AES算法，密钥长度位固定的128/192/256位，但是在压缩文件工具中输入的密码随便几位都可以。
 *    因为对称加密算法决定口令必须是固定长度，然后对明文进行分块加密。又因为安全需要，口令长度往往都在128位以上，即至少16个字符
 *    平时在压缩工具中输入的密码，并不能直接作为AES的SecretKey进行加密解密的（除非长度恰巧是128/192/256位），并且用户输入的口
 *    令一般都有规律，安全性远远不如安全随机数生成的随机口令。因此用户输入的口令之后，通常还需要使用PBE算法，采用随机数杂凑计算出
 *    真正的密钥，再进行AES加密。PBE简单理解就是用来替代SecureRandom.generateSeed(16/24/32)
 *
 *    PBE作用就是把用户输入的口令和一个安全随机的口令采用杂凑后计算出的真正的密钥。以AES密钥为例，让用户输入一个口令，然后生成
 *    一个随机数，通过PBE算法计算出真正的AES口令，在进行加密
 *
 *
 * @Author: LXR
 * @DATE: 2020/12/29 17:01
 * @Version: 1.0
 */
public class Pbe {


    public static void main(String[] args) {

        try {
            //==============================================AES的CBC模式+PEB加密===================================================
            //需要导入BouncyCastle作为Provider添加到java.security
            Security.addProvider(new BouncyCastleProvider());
            //原文
            String message = "HelloWorld";
            //加密口令，用户输入的密码
            String password = "hello123456";
            //16字节随机的Salt

            //加密
            byte[] data = message.getBytes("UTF-8");
            byte[] encrypted = encrypt(password, data);
            System.out.println("encrypted:" + Base64.getEncoder().encodeToString(encrypted));

            //==============================================AES的CBC模式+PEB解密===================================================
            byte[] decrypted = decrypt(password, encrypted);
            System.out.println("decrypted:"+new String(decrypted,"UTF-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * AES的CBC模式+PBE口令算法
     * @param password
     * @param encrypted
     * @return
     */
    public static byte[] decrypt(String password, byte[] encrypted) {

        try {
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEwithSHA1and128bitAES-CBC-BC");
            SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

            byte[] salt = SecureRandom.getInstanceStrong().generateSeed(16);
            System.out.printf("salt:%#x\n",new BigInteger(1, salt));
            PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 1000);

            Cipher cipher = Cipher.getInstance("PBEwithSHA1and128bitAES-CBC-BC");
            cipher.init(Cipher.DECRYPT_MODE,secretKey,pbeParameterSpec);
            return cipher.doFinal(encrypted);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException e) {
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
     * 加密处理，AES算法的CBC模式+PBE
     * @param password
     * @param data
     * @return
     */
    public static byte[] encrypt(String password, byte[] data) {

        try {
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEwithSHA1and128bitAES-CBC-BC");
            SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

            byte[] salt = SecureRandom.getInstanceStrong().generateSeed(16);
            System.out.printf("salt:%#x\n",new BigInteger(1, salt));
            PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 1000);

            Cipher cipher = Cipher.getInstance("PBEwithSHA1and128bitAES-CBC-BC");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, pbeParameterSpec);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
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




}
