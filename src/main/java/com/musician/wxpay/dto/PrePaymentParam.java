package com.musician.wxpay.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author: LXR
 * @since: 2021/2/2 14:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrePaymentParam {

    private Integer vipId;
    private String ip;
    private Integer originVipId;

}
