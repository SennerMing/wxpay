package com.musician.wxpay.controller;

import com.musician.wxpay.dto.OrderParam;
import com.musician.wxpay.dto.PrePaymentParam;
import com.musician.wxpay.entity.PaymentResult;
import com.musician.wxpay.service.PaymentResultService;
import com.musician.wxpay.wxservice.PaymentService;
import com.musician.wxpay.wxservice.PrePayService;
import com.musician.wxpay.wxservice.WxNotifyService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: LXR
 * @since: 2021/2/2 14:09
 */
@CrossOrigin
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class WxPaymentController {


    @PostMapping("/wx/prePay")
    @ResponseBody
    public String prePay(@RequestBody @Validated PrePaymentParam prePaymentParam){
        return prePayService.prePay(prePaymentParam);
    }

    @PostMapping("/wx/wxNotify")
    @ResponseBody
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) {
        String result = wxNotifyService.notifyResolve(request);
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/wx/orderQuery")
    @ResponseBody
    public PaymentResult orderQuery(@RequestBody @Validated OrderParam orderParam) {
        return paymentService.orderQuery(orderParam);
    }


    @Resource
    private PrePayService prePayService;

    @Resource
    private WxNotifyService wxNotifyService;

    @Resource
    private PaymentService paymentService;



}
