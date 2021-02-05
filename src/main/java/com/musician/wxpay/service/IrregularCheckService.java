package com.musician.wxpay.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.musician.wxpay.dto.IrregularCheckParam;
import com.musician.wxpay.entity.IrregularCheck;

import java.util.List;

public interface IrregularCheckService {

    IPage<IrregularCheck> searchIrregularCheckInfo(IrregularCheckParam irregularCheckParam);

    List<IrregularCheck> packageAllIrregularCheckInfo();
}
