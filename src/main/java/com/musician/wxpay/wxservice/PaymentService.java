package com.musician.wxpay.wxservice;

import cn.hutool.http.HttpUtil;
import com.musician.wxpay.dto.OrderParam;
import com.musician.wxpay.entity.PaymentResult;
import com.musician.wxpay.service.PaymentResultService;
import com.musician.wxpay.service.PrePaymentService;
import com.musician.wxpay.wxconfig.WxConfig;
import com.musician.wxpay.wxutils.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class PaymentService {

    //进行订单结果的主动查询
    public PaymentResult orderQuery(OrderParam orderParam) {
        if (orderParam.getOutTradeNo() == null) {
            log.info("商户订单号为空！");
        }
        PaymentResult paymentResult = paymentResultService.getPaymentResultByOutTradeNo(orderParam.getOutTradeNo());

        if (paymentResult.getCheckConfirm() == 1) {
            return paymentResult;
        }

        Map<String, String> orderQueryMap = new HashMap<String, String>(7);
        orderQueryMap.put("appid", paymentResult.getAppId());
        orderQueryMap.put("mch_id", paymentResult.getMch_id());
        orderQueryMap.put("transaction_id", paymentResult.getTransactionId());
        orderQueryMap.put("out_trade_no", paymentResult.getOutTradeNo());
        orderQueryMap.put("nonce_str", WXPayUtil.generateNonceStr());
        try {

            String signxml = "";
            try {
                signxml = WXPayUtil.generateSignedXml(orderQueryMap, wxConfig.getApikey());
            } catch (Exception e) {
                e.printStackTrace();
            }
            String responseXmlStr = HttpUtil.post(wxConfig.getOrderQueryUrl(), signxml);
            Map<String,String> responseMap = WXPayUtil.xmlToMap(responseXmlStr);

            if (responseMap.get("return_code").equals("SUCCESS")) { //首先是否是正确的返回

                String result_code = responseMap.get("result_code");
                String trade_state = responseMap.get("trade_state");

                if(result_code.equals("SUCCESS") && trade_state.equals("SUCCESS")){

                    if (WXPayUtil.isSignatureValid(responseXmlStr, wxConfig.getApikey())) {
                        String total_fee = responseMap.get("total_fee");
                        String time_end = responseMap.get("time_end");
                        if(total_fee.equals(paymentResult.getTotalFee())){
                            paymentResult.setTransactionId(responseMap.get("transaction_id"));
                            paymentResult.setOpenId(responseMap.get("openid"));
                            paymentResult.setIsSubscribe(responseMap.get("is_subscribe"));
                            paymentResult.setBankType(responseMap.get("bank_type"));
                            paymentResult.setCheckConfirm(1);
                            paymentResult.setMch_id(responseMap.get("mch_id"));
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                            paymentResult.setTimeEnd(LocalDateTime.parse(time_end,formatter));
                            paymentResult.setTradeState("SUCCESS");
                            int updateCount = paymentResultService.updatePaymentResult(paymentResult);
                            if(updateCount > 0){
                                log.info("付款成功！支付完成后置处理开始！");
                                log.info("======================>> 开始更新预付单信息  <<======================");
                                int updatePre = prePaymentService.updateTradeState(paymentResult.getOutTradeNo(), 1);
                                log.info("TAB_PAYMENT_RESULT --> ID:["+paymentResult.getId()+"] 预付单支付状态已更新！");

                                log.info("======================>> 开始更新用户会员信息  <<======================");
                                //1.修改会员类型与其对应的会员名称 ENTERPRISE_OPINION_VIP --> vip_id、vip_type，
                                //2.需要更新enterprise_opinion_recharge_records

                            }else{
                                log.info("TAB_PAYMENT_RESULT --> ID:["+paymentResult.getId()+"] 支付结果已经被确认过！请勿重复操作！");
                            }
                        }
                    }
                }else{
                    paymentResult.setTradeState(trade_state);
                    log.info("当前订单状态为：" + trade_state);
                }
            }else{
                String return_msg = responseMap.get("return_msg");
                log.info("查询失败 ----> " + return_msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentResult;
    }


    @Resource
    private PaymentResultService paymentResultService;

    @Resource
    private PrePaymentService prePaymentService;

    @Resource
    private WxConfig wxConfig;
}
