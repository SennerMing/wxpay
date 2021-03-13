package com.musician.wxpay.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.musician.wxpay.dao.PrePaymentMapper;
import com.musician.wxpay.entity.PrePayment;
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
public class PrePaymentServiceImpl implements PrePaymentService {
    @Override
    public int addPrePayment(PrePayment prePayment) {
        return prePaymentMapper.insert(prePayment);
    }

    @Override
    public int updateTradeState(String outTradeNo, int tradeState) {
        LambdaUpdateWrapper<PrePayment> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
        lambdaUpdateWrapper.eq(PrePayment::getOutTradeNo, outTradeNo).eq(PrePayment::getTradeState, 0)
                .set(PrePayment::getTradeState, tradeState);
        return prePaymentMapper.update(null, lambdaUpdateWrapper);
    }

    @Resource
    private PrePaymentMapper prePaymentMapper;

}
