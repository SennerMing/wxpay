package com.musician.wxpay.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Accessors(chain = true)
@TableName(value = "tab_vip_order")
public class PrePayment {
    private Integer id;

    @TableField
    private String outTradeNo;

    @TableField
    private String prepayId;

    @TableField
    private String tradeType;

    @TableField
    private String appId;

    @TableField
    private String mchId;

    @TableField
    private String deviceInfo;

    @TableField
    private String nonceStr;

    @TableField
    private String sign;

    @TableField
    private Integer tradeState;

    @TableField
    private String payIp;

    @TableField
    private String totalFee;

    @TableField
    private Integer vipId;

    @TableField
    private String body;

    @TableField
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedTime;
}
