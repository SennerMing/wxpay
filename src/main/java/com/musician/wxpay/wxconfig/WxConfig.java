package com.musician.wxpay.wxconfig;

import com.musician.wxpay.customize.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:wxconfig.yaml")
@Data
public class WxConfig {

    @Value("${wxpay.confidential.mch_id}")
    private String mchId;
    @Value("${wxpay.confidential.api_key}")
    private String apikey;
    @Value("${wxpay.confidential.app_id}")
    private String appId;
    @Value("${wxpay.confidential.app_secret}")
    private String appSecret;

    @Value("${wxpay.url.unifiedorder_url}")
    private String unifiedorderUrl;
    @Value("${wxpay.url.pre_pay_notify_url}")
    private String prePayNotifyUrl;
    @Value("${wxpay.url.order_query_url}")
    private String orderQueryUrl;

}
