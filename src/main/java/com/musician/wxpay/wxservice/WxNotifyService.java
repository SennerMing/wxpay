package com.musician.wxpay.wxservice;

import com.musician.wxpay.entity.PaymentResult;
import com.musician.wxpay.service.PaymentResultService;
import com.musician.wxpay.service.PrePaymentService;
import com.musician.wxpay.wxconfig.WxConfig;
import com.musician.wxpay.wxutils.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
@Slf4j
public class WxNotifyService {

    @Resource
    private WxConfig wxConfig;

    public String notifyResolve(HttpServletRequest request) {

        String api_key = wxConfig.getApikey();
        log.info("微信正在回调》》》》》》》》》");

        String xmlString = "";
        String lastXml = "";

        try {
            xmlString = getXmlString(request);
//            log.info("微信返回的回调结果是：：：：：：：" + xmlString);
            // 先解析返回的数据
            Map<String, String> dataMap = WXPayUtil.xmlToMap(xmlString);
            String returnCode = dataMap.get("return_code");
            // 通信成功
            if ("SUCCESS".equals(returnCode)) {
                log.info("通信成功++++++++++++");

                // 验证通过才能记到流水表中，否则不计入
                if (WXPayUtil.isSignatureValid(xmlString, api_key)) {
//                if(true){
                    try {
                        String transactionId = dataMap.get("transaction_id");
                        String outTradeNo = dataMap.get("out_trade_no");
                        String openId = dataMap.get("openid");
                        String is_subscribe = dataMap.get("is_subscribe");
                        String bank_type = dataMap.get("bank_type");
                        String time_end = dataMap.get("time_end");
                        String mch_id = dataMap.get("mch_id");

                        if (dataMap.get("result_code").equals("SUCCESS")) {

                            String total_fee = dataMap.get("total_fee");

                            PaymentResult paymentResult = paymentResultService.getPaymentResultByOutTradeNo(outTradeNo);
                            if((paymentResult.getCheckConfirm() == 0) && total_fee.equals(paymentResult.getTotalFee())){

                                //进行金额比对是否相同
                                paymentResult.setTransactionId(transactionId);
                                paymentResult.setOpenId(openId);
                                paymentResult.setIsSubscribe(is_subscribe);
                                paymentResult.setBankType(bank_type);
                                paymentResult.setCheckConfirm(1);
                                paymentResult.setMch_id(mch_id);
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                                paymentResult.setTimeEnd(LocalDateTime.parse(time_end,formatter));
                                paymentResult.setTradeState("SUCCESS");
                                // 设置为已经回调,更新数据库中的信息
                                int updateCount = paymentResultService.updatePaymentResult(paymentResult);
                                if(updateCount > 0){
                                    log.info("付款成功！支付完成后置处理开始！");
                                    log.info("======================>> 开始更新预付单信息  <<======================");
                                    int updatePre = prePaymentService.updateTradeState(outTradeNo, 1);
                                    log.info("TAB_PAYMENT_RESULT --> ID:["+paymentResult.getId()+"] 预付单支付状态已更新！");

                                    log.info("======================>> 开始更新用户会员信息  <<======================");
                                    //1.修改会员类型与其对应的会员名称 ENTERPRISE_OPINION_VIP --> vip_id、vip_type，
                                    //2.需要更新enterprise_opinion_recharge_records

                                }else{
                                    log.info("TAB_PAYMENT_RESULT --> ID:["+paymentResult.getId()+"] 支付结果已经被确认过！请勿重复操作！");
                                }
                                lastXml = returnXML("SUCCESS","OK");
                            }else{
                                lastXml = returnXML("SUCCESS","NOT OK");
                                log.info("金额不相符");
                            }

                        } else {
                            //设置预付单信息付款失败
                            int updatePre = prePaymentService.updateTradeState(outTradeNo, -1);
                            lastXml = returnXML("SUCCESS","NOT OK");
                            log.info("付款失败！");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else {

            }
        } catch (Exception e) {
            log.info("支付失败，出现异常！");
        }
        log.info("最终给微信的结果是：" + lastXml);
        return lastXml;
    }

    /**
     * IO解析获取微信的数据
     *
     * @param request
     * @return
     */
    private String getXmlString(HttpServletRequest request) {
//        BufferedReader reader = null;
//        String line = "";
//        String xmlString = null;
//        try {
//            reader = request.getReader();
//            StringBuffer inputString = new StringBuffer();
//
//            while ((line = reader.readLine()) != null) {
//                inputString.append(line);
//            }
//            xmlString = inputString.toString();
//        } catch (Exception e) {
//            log.info("微信 Notify 解析失败！");
//        }

        String xmlString = "<xml>\n" +
                "  <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
                "  <attach><![CDATA[支付测试]]></attach>\n" +
                "  <bank_type><![CDATA[CFT]]></bank_type>\n" +
                "  <fee_type><![CDATA[CNY]]></fee_type>\n" +
                "  <is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
                "  <mch_id><![CDATA[10000100]]></mch_id>\n" +
                "  <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>\n" +
                "  <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>\n" +
                "  <out_trade_no><![CDATA[819923051603820544]]></out_trade_no>\n" +
                "  <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>\n" +
                "  <time_end><![CDATA[20140903131540]]></time_end>\n" +
                "  <total_fee>17191</total_fee>\n" +
                "  <coupon_fee><![CDATA[10]]></coupon_fee>\n" +
                "  <coupon_count><![CDATA[1]]></coupon_count>\n" +
                "  <coupon_type><![CDATA[CASH]]></coupon_type>\n" +
                "  <coupon_id><![CDATA[10000]]></coupon_id>\n" +
                "  <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                "  <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>\n" +
                "</xml>";
        return xmlString;
    }

    /**
     * 返回给微信服务端的xml
     * @param return_code
     * @return
     */
    private String returnXML(String return_code,String msg) {
        return "<xml><return_code><![CDATA["
                + return_code
                + "]]></return_code><return_msg><![CDATA["+msg+"]]></return_msg></xml>";
    }

    @Resource
    private PaymentResultService paymentResultService;

    @Resource
    private PrePaymentService prePaymentService;

}
