package com.musician.wxpay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *    关于加密部分：HMAC（Hash加盐，防止彩虹表攻击），对称AES及可以让用户自定义密码的对称加密PBE、密码交换算法DH、非对称加密算法RSA、
 *    与RSA相结合的签名算法HashwithRSA、DSA、ECDSA，大部分都抄自廖雪峰
 */
@SpringBootApplication
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
public class WxpayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxpayApplication.class, args);
    }

}
