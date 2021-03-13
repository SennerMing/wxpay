package com.musician.wxpay.wxservice;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.musician.wxpay.dto.PrePaymentParam;
import com.musician.wxpay.entity.PaymentResult;
import com.musician.wxpay.entity.PrePayment;
import com.musician.wxpay.orderutils.SnowflakeIdWorker;
import com.musician.wxpay.service.PaymentResultService;
import com.musician.wxpay.service.PrePaymentService;
import com.musician.wxpay.wxconfig.WxConfig;
import com.musician.wxpay.wxutils.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class PrePayService {


    public String prePay(PrePaymentParam prePaymentParam) {
        String api_key = wxConfig.getApikey();  //上面在商户号中使用别人网站设置的API秘钥;
        String app_id = wxConfig.getAppId();    //微信开放平台应用ID
        String mch_id = wxConfig.getMchId();    //商户号ID
        String unifiedorder_url = wxConfig.getUnifiedorderUrl();    //统一下单：https://api.mch.weixin.qq.com/pay/unifiedorder
        String pre_pay_callback = wxConfig.getPrePayNotifyUrl(); //随便填写的公司地址，外网可以访问到的
        String expire_time = "2021-07-17 00:00:00";
        String googDesc = "填写竞标助手会员信息";

        Map<String, String> param = new HashMap<String, String>();
        param.put("appid",app_id);
        param.put("mch_id",mch_id);
        String nonce_str = WXPayUtil.generateNonceStr();
        param.put("nonce_str",nonce_str);
        param.put("body",googDesc);
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        long id = idWorker.nextId();
        String out_trade_no = String.valueOf(id);

        System.out.println("out_trade_no.length:"+out_trade_no.length());
        param.put("out_trade_no",out_trade_no);

        //计算价格
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.parse(expire_time, formatter);
        Duration duration = Duration.between(startTime, endTime);
        long dayNum = duration.toDays();

        BigDecimal purchasePrice = vipPriceMap.get(prePaymentParam.getVipId());
        BigDecimal originPrice = vipPriceMap.get(prePaymentParam.getOriginVipId());
        BigDecimal dayNumDB = new BigDecimal(String.valueOf(dayNum));
        BigDecimal middleValue = (purchasePrice.subtract(originPrice)).multiply(dayNumDB);
        BigDecimal price = middleValue.divide(new BigDecimal("365"),2,BigDecimal.ROUND_HALF_UP);
        String totla_fee = price.toPlainString().replace(".", "");

        param.put("total_fee",totla_fee);
        param.put("spbill_create_ip", prePaymentParam.getIp());
        param.put("notify_url", pre_pay_callback);
        String trade_type = "APP";
        param.put("trade_type",trade_type);

        String signxml = "";
        try {
            signxml = WXPayUtil.generateSignedXml(param, api_key);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("sign:"+signxml);
//        param.put("sign",signxml);

//        String xmlStr = HttpRequest.httpsRequest(unifiedorder_url, "POST", signxml);
        String xmlStr = HttpUtil.post(unifiedorder_url, signxml);

//        System.out.println(xmlStr);
        // 以下内容是返回前端页面的json数据
        String prepay_id = "";// 预支付id
        if (xmlStr.indexOf("SUCCESS") != -1) {
            Map<String, String> map = null;
            //需要进行验签操作
            try {
                if(WXPayUtil.isSignatureValid(xmlStr, api_key)){
                    try {
                        map = WXPayUtil.xmlToMap(xmlStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            prepay_id = (String) map.get("prepay_id");
            trade_type = (String) map.get("trade_type");
        }

        Map<String, String> payMap = new HashMap<String, String>();
        payMap.put("appId", app_id);
        payMap.put("tradeType", trade_type);
        payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp() + "");
        payMap.put("nonceStr", WXPayUtil.generateNonceStr());
        payMap.put("signType", "MD5");
        payMap.put("outTradeNo", out_trade_no);
        payMap.put("package", "prepay_id=" + prepay_id);
        String prePaySign = null;
        try {
            prePaySign = WXPayUtil.generateSignature(payMap, api_key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        payMap.put("paySign", prePaySign);

        //可以将信息整理入库了
        PrePayment prePayment = PrePayment.builder().appId(app_id).body("竞标助手VIP").deviceInfo("").mchId(mch_id)
                .nonceStr(WXPayUtil.generateNonceStr()).outTradeNo(out_trade_no).payIp(prePaymentParam.getIp())
                .prepayId(prepay_id).sign(prePaySign).totalFee(totla_fee).tradeState(0).tradeType(trade_type)
                .vipId(prePaymentParam.getVipId()).build();

        prePaymentService.addPrePayment(prePayment);

        PaymentResult paymentResult = PaymentResult.builder().appId(app_id).outTradeNo(out_trade_no).nonceStr(WXPayUtil.generateNonceStr())
                .sign(prePaySign).totalFee(totla_fee).tradeType(trade_type).build();
        paymentResultService.initPayment(paymentResult);
        return JSONUtil.parse(payMap).toString();
    }

    @Resource
    private PrePaymentService prePaymentService;

    @Resource
    private PaymentResultService paymentResultService;

    @Resource
    private WxConfig wxConfig;

    private static Map<Integer, BigDecimal> vipPriceMap = new HashMap<>();

    static {
        vipPriceMap.put(1, new BigDecimal("0.0"));
        vipPriceMap.put(2, new BigDecimal("498.0"));
        vipPriceMap.put(3, new BigDecimal("998.0"));
    }

}
