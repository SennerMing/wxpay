package com.musician.wxpay.service.impl;

import com.musician.wxpay.dao.SensitiveTypeMapper;
import com.musician.wxpay.entity.SensitiveType;
import com.musician.wxpay.service.SensitiveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: LXR
 * @since: 2021/1/28 15:00
 */
@Service
public class SensitiveTypeServiceImpl implements SensitiveTypeService {

    @Autowired
    private SensitiveTypeMapper sensitiveTypeMapper;

    @Override
    public void packageAllSentiveWordsByType(SensitiveType sensitiveType) {
        List<SensitiveType> sensitiveTypeList = sensitiveTypeMapper.selectList(null);
        System.out.println(sensitiveTypeList.size());
    }
}
