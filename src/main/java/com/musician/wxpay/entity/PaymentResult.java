package com.musician.wxpay.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@Accessors(chain = true)
@TableName(value = "tab_payment_result")
public class PaymentResult {
    private Integer id;

    @TableField
    private String mch_id;

    @TableField
    private String outTradeNo;

    @TableField
    private String userId;

    @TableField
    private String orgId;

    @TableField
    private Integer checkConfirm;

    @TableField
    private String appId;

    @TableField
    private String nonceStr;

    @TableField
    private String sign;

    @TableField
    private String openId;

    @TableField
    private String isSubscribe;

    @TableField
    private String tradeType;

    @TableField
    private String bankType;

    @TableField
    private String totalFee;

    @TableField
    private String cashFee;

    @TableField
    private String transactionId;

    @TableField
    private String tradeState;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime timeEnd;

    @TableField
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedTime;


}
