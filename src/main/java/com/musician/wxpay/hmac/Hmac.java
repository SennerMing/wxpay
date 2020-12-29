package com.musician.wxpay.hmac;

import com.musician.wxpay.utils.NumberConvertUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: Hmac
 * @Description:
 * 关于Hmac算法，全称Hash-based Message Authentication Code，很像Hash+salt为的是抵御彩虹表（撞库）的攻击
 *
 * =================================================================================================
 * Q&A -- 为什么会出现彩虹表攻击
 *     -- 因为Hash算法使用时，相同的输入会产生相同的输出，将Hash码反推明文口令，费时费力，但是如果你的明文口令很简单，
 *        恰巧黑客也拥有一张简单明文口令与Hash码的对照表，那么黑客就很容易的获取到你的明文口令，如果你的密码为：123456，
 *        假设黑客得到了你的MD5码为zzzzzzz,那么黑客可以通过下表，很容易的就获取到你的明文。所以密码尽量设置的复杂一些！
 *        黑客的对照表：
 *                 明文        hash码      username
 *          1  |  hello  |  xxxxxxx   |  xiao_hong |
 *          2  |  123456 |  zzzzzzz   |  xiao_lv   |
 *          3  |  753951 |  yyyyyyy   |  xiao_lan  |
 * 应对以上攻击获取用户密码的手段，服务端可以通过加盐处理，即使用户的密码设置的很简单，那么通过加salt，就可以使彩虹表失效。
 * =================================================================================================
 *
 * =================================================================================================
 * HmacMD5可以理解为自带安全key的MD5算法
 * Hmac的key长度为64字节，相对安全
 * Hmac是标准算法，除了MD5可以与SHA-1等哈希算法组合使用
 * Hmac输出和原有的哈希算法长度一致
 * =================================================================================================
 *
 * Hmac实际就是把key混入Digest（摘要）的算法
 *
 * =================================================================================================
 *
 * Hmac算法是一种标准的基于密钥的哈希算法，可以配合MD5、SHA-1等哈希算法，计算的摘要长度和原摘要算法长度相同。
 *
 * @Author: LXR
 * @DATE: 2020/12/29 9:12
 * @Version: 1.0
 */
public class Hmac {

    public static void main(String[] args) {

        try {
            //由Hmac的特性得知，他是一个自带加盐的哈希算法，所以我们可以通过他生成key确保安全。(自己生成的也可以，但是本性驱使我们要使用工具)。
            //获得一个HmacMD5的KeyGenerator实例
            // -- MD5为典型的哈希算法，常见的Hash算法还有SHA-1[长度太短，短期内容易被暴力破解，废弃掉了]、RipeMD-160、SHA-256、SHA-512
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacMD5");
            SecretKey key = keyGen.generateKey();
            byte[] skey = key.getEncoded();
            //一、new BigInteger(signum,magnitude)
            // -- BigInteger 可以表示远超Integer表示范围的整数
            // -- signum 表示通过此构造器生成的整数的符号，可取值为 -1 0 1
            // -- magnitude 一个byte[] 例如：{28,56,125,....}
            //二、toString(radix) 将BigInteger转换为radix进制的字符串
            System.out.println("生成密钥："+new BigInteger(1,skey).toString(16));
            //将HmacMD5生成的key转换为一个16进制的正整数字符串
            String skeystr = new BigInteger(1,skey).toString(16);

            //============================================加密=====================================================
            //获得一个HmacMD5的实例
            Mac mac = Mac.getInstance("HmacMD5");
            //初始化key        -- 加盐
            mac.init(key);
            //将密码添加入HmacMD5内
            mac.update("HelloWorld2".getBytes("UTF-8"));
            byte[] result = mac.doFinal();
            //获得加盐处理过后的密码结果     -- 同样是正整数16进制的字符串
            System.out.println("1.生成密钥与明文混合结果："+new BigInteger(1,result).toString(16));

            //========================================恢复key进行对比=================================================
            //skeystr 可以将第一步中生成的key保存到数据库中，skeystr可以从数据库中获取
//            skeystr = "96f40dd8eebe4f85c76843bf18ee7798294ec4cf8f50c83a5a16" +
//                    "dee3fda5c963d3f3f6399c6b690362898e4705e74b2544380bb1f5154d21964696711932cbac";
            byte[] renew_skey = NumberConvertUtils.hexStr2bytes(skeystr);
            //获得一个HmacMD5实例，并将skeystr恢复
            SecretKey dekey = new SecretKeySpec(renew_skey, "HmacMD5");
            //获得一个HmacMD5实例
            Mac demac = Mac.getInstance("HmacMD5");
            //初始化key        -- 加盐
            demac.init(dekey);
            //加入用户输入的密码
            demac.update("HelloWorld2".getBytes("UTF-8"));
            byte[] deresult = demac.doFinal();
            //获得此次用户输入密码及加盐后的结果
            System.out.println("2.恢复密钥与明文混合结果："+new BigInteger(1,deresult).toString(16));

            System.out.println("两相对比，是否一致，一致则通过密码验证通过！");
            //接着与上面的加盐处理后的密码结果进行对比，如果相同，则成功登录！


        } catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}
