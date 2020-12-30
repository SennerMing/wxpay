package com.musician.wxpay.asymmetric;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;

/**
 * @ClassName: Rsa
 * @Description:
 *    DH算法使用到公钥-私钥组成的密钥对，奠定了非对称加密的基础。
 *    -- 对称加密的实现中重要的一环是需要协商密钥
 *    -- 非对称加密可以对外开放自己的公钥
 *
 * @Author: LXR
 * @since: 2020/12/30 10:59
 * @Version: 1.0
 */
public class Rsa {
    public static void main(String[] args) throws GeneralSecurityException {
        // 明文:
        byte[] plain = new byte[0];
        try {
            plain = "Hello, encrypt use RSA".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 创建公钥／私钥对:
        Person alice = new Person("Alice");
        // 用Alice的公钥加密:
        byte[] pk = alice.getPublicKey();
        System.out.println(String.format("public key: %x", new BigInteger(1, pk)));
        byte[] encrypted = alice.encrypt(plain);
        System.out.println(String.format("encrypted: %x", new BigInteger(1, encrypted)));
        // 用Alice的私钥解密:
        byte[] sk = alice.getPrivateKey();
        System.out.println(String.format("private key: %x", new BigInteger(1, sk)));
        byte[] decrypted = alice.decrypt(encrypted);
        try {
            System.out.println(new String(decrypted, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
class Person {
    String name;
    // 私钥:
    PrivateKey sk;
    // 公钥:
    PublicKey pk;

    public Person(String name) throws GeneralSecurityException {
        this.name = name;
        // 生成公钥／私钥对:
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(1024);
        KeyPair kp = kpGen.generateKeyPair();
        this.sk = kp.getPrivate();
        this.pk = kp.getPublic();
    }

    // 把私钥导出为字节
    public byte[] getPrivateKey() {
        return this.sk.getEncoded();
    }

    // 把公钥导出为字节
    public byte[] getPublicKey() {
        return this.pk.getEncoded();
    }

    // 用公钥加密:
    public byte[] encrypt(byte[] message) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, this.pk);
        return cipher.doFinal(message);
    }

    // 用私钥解密:
    public byte[] decrypt(byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, this.sk);
        return cipher.doFinal(input);
    }

    /**
     * RSA的公钥和私钥都可以通过getEncoded()方法获得以byte[]表示的二进制数据，并根据需要保存到文件中。
     * 要从byte[]数组恢复公钥或私钥，可以这么写：
     *
     *  byte[] pkData = ...
     *  byte[] skData = ...
     *  KeyFactory kf = KeyFactory.getInstance("RSA");
     *  // 恢复公钥:
     *  X509EncodedKeySpec pkSpec = new X509EncodedKeySpec(pkData);
     *  PublicKey pk = kf.generatePublic(pkSpec);
     *  // 恢复私钥:
     *  PKCS8EncodedKeySpec skSpec = new PKCS8EncodedKeySpec(skData);
     *  PrivateKey sk = kf.generatePrivate(skSpec);
     *
     *  以RSA算法为例，它的密钥有256/512/1024/2048/4096等不同的长度。长度越长，密码强度越大，当然计算速度也越慢。
     *  如果修改待加密的byte[]数据的大小，可以发现，使用512bit的RSA加密时，明文长度不能超过53字节，使用1024bit的RSA加密时，
     *  明文长度不能超过117字节，这也是为什么使用RSA的时候，总是配合AES一起使用，即用AES加密任意长度的明文，用RSA加密AES口令。
     *  此外，只使用非对称加密算法不能防止中间人攻击。
     *
     */


}