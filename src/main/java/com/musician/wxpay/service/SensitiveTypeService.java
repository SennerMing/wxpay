package com.musician.wxpay.service;


import com.musician.wxpay.entity.SensitiveType;

public interface SensitiveTypeService {

    void packageAllSentiveWordsByType(SensitiveType sensitiveType);

}
