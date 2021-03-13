package com.musician.wxpay.service;


import com.musician.wxpay.entity.PaymentResult;

public interface PaymentResultService {

    int initPayment(PaymentResult paymentResult);

    PaymentResult getPaymentResultByOutTradeNo(String outTradeNo);

    boolean checkConfirm(String outTradeNo, int checkConfirm);

    int updatePaymentResult(PaymentResult paymentResult);

}
