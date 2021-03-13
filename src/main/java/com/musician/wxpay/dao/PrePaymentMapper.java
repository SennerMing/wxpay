package com.musician.wxpay.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.musician.wxpay.entity.PrePayment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: LXR
 * @since: 2021年2月2日 13点36分
 */
@Mapper
@Repository
public interface PrePaymentMapper extends BaseMapper<PrePayment> {


}
