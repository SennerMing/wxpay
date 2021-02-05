package com.musician.wxpay.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.musician.wxpay.dto.IrregularCheckParam;
import com.musician.wxpay.entity.IrregularCheck;
import com.musician.wxpay.service.IrregularCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: LXR
 * @since: 2021/2/2 14:09
 */
@CrossOrigin
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class IrregularCheckController {

    @Autowired
    private IrregularCheckService irregularCheckService;

    @PostMapping("/api/v1/searchIrregularCheckInfo")
    @ResponseBody
    public IPage<IrregularCheck> searchIrregularCheckInfo(IrregularCheckParam irregularCheckParam){
        IPage<IrregularCheck> irregularChecks = irregularCheckService.searchIrregularCheckInfo(irregularCheckParam);
        return irregularChecks;
    }

    @GetMapping("/api/v1/getAllIrregularCheckInfo")
    @ResponseBody
    public List<IrregularCheck> getAllIrregularCheckInfo(){
        List<IrregularCheck> irregularChecks = irregularCheckService.packageAllIrregularCheckInfo();
        return irregularChecks;
    }


}
