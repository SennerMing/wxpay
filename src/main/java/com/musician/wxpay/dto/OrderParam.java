package com.musician.wxpay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderParam {
    private String appId;
    private String mchId;
    private String transactionId;
    private String outTradeNo;
    private String nonceStr;
    private String sign;
    private String signType;
}
