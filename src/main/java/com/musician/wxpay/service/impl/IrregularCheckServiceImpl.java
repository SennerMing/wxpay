package com.musician.wxpay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.musician.wxpay.dao.IrregularCheckMapper;
import com.musician.wxpay.dto.IrregularCheckParam;
import com.musician.wxpay.entity.IrregularCheck;
import com.musician.wxpay.service.IrregularCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: LXR
 * @since: 2021/2/2 13:48
 */
@Service
@Slf4j
public class IrregularCheckServiceImpl implements IrregularCheckService {

    @Autowired
    private IrregularCheckMapper irregularCheckMapper;

    @Override
    public IPage<IrregularCheck> searchIrregularCheckInfo(IrregularCheckParam irregularCheckParam) {
        LambdaQueryWrapper<IrregularCheck> lambdaQuery = Wrappers.lambdaQuery();
        IrregularCheck irregularCheck = new IrregularCheck();
        if (!StringUtils.isBlank(irregularCheckParam.getWebsiteTypeName())) {
            lambdaQuery.eq(IrregularCheck::getWebsiteType, irregularCheckParam.getWebsiteTypeName());
        }
        if (!StringUtils.isBlank(irregularCheckParam.getWebsiteName())) {
            lambdaQuery.eq(IrregularCheck::getWebsiteName, irregularCheckParam.getWebsiteName());
        }
        if (!StringUtils.isBlank(irregularCheckParam.getSensitiveTypeName())) {
            lambdaQuery.eq(IrregularCheck::getSensitiveTypes, irregularCheckParam.getSensitiveTypeName());
        }
        if (!StringUtils.isBlank(irregularCheckParam.getSensitiveWord())) {
            lambdaQuery.like(IrregularCheck::getSensitiveWords, irregularCheckParam.getSensitiveWord());
        }
        if ((irregularCheckParam.getStartCheckDate() != null) && (irregularCheckParam.getStartCheckDate() != null)) {
            lambdaQuery.between(IrregularCheck::getCheckDate, irregularCheckParam.getStartCheckDate(), irregularCheckParam.getEndCheckDate());
        }
        Page<IrregularCheck> page = new Page<>(irregularCheckParam.getPage(), irregularCheckParam.getPageNum());
        IPage<IrregularCheck> irregularCheckIPage = irregularCheckMapper.selectIrregularPage(page, lambdaQuery);
        return irregularCheckIPage;
    }

    @Override
    public List<IrregularCheck> packageAllIrregularCheckInfo() {
        List<IrregularCheck> irregularChecks = irregularCheckMapper.selectList(null);
        return irregularChecks;
    }
}
