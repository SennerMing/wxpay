package com.musician.wxpay.controller;

import com.musician.wxpay.dao.SensitiveTypeMapper;
import com.musician.wxpay.entity.SensitiveType;
import com.musician.wxpay.service.SensitiveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: LXR
 * @since: 2021/1/28 14:55
 */
public class SensitiveController {


    @Autowired
    private SensitiveTypeService sensitiveTypeService;


    @PostMapping("/api/v1/getOrgSensitive")
    public void exportAEUData(@RequestParam @Validated SensitiveType sensitiveType){
        sensitiveTypeService.packageAllSentiveWordsByType(sensitiveType);
    }
}
