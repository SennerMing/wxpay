package com.musician.wxpay.service;


import com.musician.wxpay.entity.PrePayment;

public interface PrePaymentService {

    int addPrePayment(PrePayment prePayment);

    int updateTradeState(String outTradeNo, int tradeState);

}
