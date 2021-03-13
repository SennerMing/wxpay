package com.musician.wxpay.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.musician.wxpay.dao.PaymentResultMapper;
import com.musician.wxpay.dao.PrePaymentMapper;
import com.musician.wxpay.entity.PaymentResult;
import com.musician.wxpay.entity.PrePayment;
import com.musician.wxpay.service.PaymentResultService;
import com.musician.wxpay.service.PrePaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: LXR
 * @since: 2021/2/2 13:48
 */
@Service
@Slf4j
public class PaymentResultServiceImpl implements PaymentResultService {

    @Override
    public int initPayment(PaymentResult paymentResult) {
        return paymentResultMapper.insert(paymentResult);
    }

    @Override
    public PaymentResult getPaymentResultByOutTradeNo(String outTradeNo) {
        LambdaQueryWrapper<PaymentResult> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(PaymentResult::getOutTradeNo, outTradeNo);
        PaymentResult paymentResult = paymentResultMapper.selectOne(lambdaQueryWrapper);
        return paymentResult;
    }

    @Override
    public boolean checkConfirm(String outTradeNo, int checkConfirm) {
        LambdaQueryWrapper<PaymentResult> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(PaymentResult::getOutTradeNo, outTradeNo).eq(PaymentResult::getCheckConfirm,checkConfirm);
        int count = paymentResultMapper.selectCount(lambdaQueryWrapper);
        return count > 0 ? true : false;
    }

    @Override
    public int updatePaymentResult(PaymentResult paymentResult) {
        LambdaQueryWrapper<PaymentResult> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(PaymentResult::getOutTradeNo, paymentResult.getOutTradeNo()).eq(PaymentResult::getCheckConfirm,0);
        return paymentResultMapper.update(paymentResult, lambdaQueryWrapper);
    }

    @Resource
    private PaymentResultMapper paymentResultMapper;

}
