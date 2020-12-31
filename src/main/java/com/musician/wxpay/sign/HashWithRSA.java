package com.musician.wxpay.sign;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;

/**
 * @author: LXR
 *    对于非对称加密，一对公钥-私钥，通常是公钥加密，私钥解密，如果使用私钥加密，公钥解密也同样是可以的，因为私钥就只有持有人知道，
 *    公钥是公开存在的，如果用私钥加密得到的密文就是一个“数字签名”了，要验证这个消息的签名是否正确，只有使用这个私钥的公钥进行解密验证。
 *    使用数字签名的目的就是为了确认某个信息确实是由某个人发送的，任何人都无法伪造这个消息，并且发送方也无法抵赖。
 *
 *    在实际应用当中，签名大多不是针对原始消息进行加密，而是针对原始消息的哈希进行签名。
 *    signature = encrypt(privateKey,hash(message));
 *    对签名进行验证，使用公钥进行解密
 *    hash = decrypt(publicKey,signature);
 *    最后机密后的哈希，与原始消息的哈希进行对比。
 *
 *    私钥相当于用户的身份证ID，公钥用来给外部验证用户身份。
 *    常用的数字签名算法有(实际就是Hash和非对称加密算法的组合吧)：
 *      --  MD5withRSA
 *      --  SHA1withRSA
 *      --  SHA256withRSA
 *
 *    其他的签名算法：
 *    1.DSA
 *    全称：Digital Signature Algorithm，它使用的是ELGamal加密算法[该加密算法是基于DH密钥交换的非对称加密算法]
 *    DSA只支持SHA哈希算法：
 *      --  SHA1withDSA
 *      --  SHA256withDSA
 *      --  SHA512withDSA
 *    和RSA数字签名相比，DSA的优点是更快。
 *    2.ECDSA
 *    全称：Elliptic Curve Digital Signature Algorithm（椭圆曲线数字签名算法，啧啧啧），特点是可以从私钥推出公钥。
 *    比特币签名算法就采用了ECDSA算法，使用标准椭圆曲线secp256k1。BouncyCastle提供了ECDSA的完整实现。
 *    与AES对称加密算法不同，ECDSA不会对数据进行加密或者阻止别人看到数据本身，他就是用以确保数据没有被篡改的。
 *
 * @since: 2020/12/30 14:46
 */
public class HashWithRSA {
    public static void main(String[] args) throws GeneralSecurityException {
        // 生成RSA公钥/私钥:
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(1024);
        KeyPair kp = kpGen.generateKeyPair();
        PrivateKey sk = kp.getPrivate();
        PublicKey pk = kp.getPublic();

        // 待签名的消息:
        byte[] message = "Hello, I am Bob!".getBytes(StandardCharsets.UTF_8);

        // 用私钥签名:
        Signature s = Signature.getInstance("SHA1withRSA");
        s.initSign(sk);
        s.update(message);
        byte[] signed = s.sign();
        System.out.println(String.format("signature: %x", new BigInteger(1, signed)));

        // 用公钥验证:
        // 使用其他任何公钥进行签名的验证，或者原始信息有修改，都无法验证成功！牛啤🐂🍺
        Signature v = Signature.getInstance("SHA1withRSA");
        v.initVerify(pk);
        v.update(message);
        boolean valid = v.verify(signed);
        System.out.println("valid? " + valid);

    }

    /**
     * 小结：
     *
     * 数字签名就是用发送方的私钥对原始数据进行签名，只有用发送方公钥才能通过签名验证。
     * 数字签名用于：
     *  --  防止伪造；
     *  --  防止抵赖；
     *  --  检测篡改。
     * 常用的数字签名算法包括：MD5withRSA／SHA1withRSA／SHA256withRSA／SHA1withDSA／SHA256withDSA／SHA512withDSA／ECDSA等。
     */
}
