package com.dayuanit.dymall.controller;

import com.dayuanit.dymall.dao.PayServiceDao;
import com.dayuanit.dymall.domain.PayChannel;
import com.dayuanit.dymall.dto.AjaxResultDTO;
import com.dayuanit.dymall.exception.DyMallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayServiceDao payServiceDao;

    private static final Logger log = LoggerFactory.getLogger(PayController.class);

    @RequestMapping("/listPayChannel")
    @ResponseBody
    public AjaxResultDTO listPayChannel(){
        try {
            List<PayChannel> payChannelList = payServiceDao.listPayChannel();
            System.out.println("payChannelList size is " + payChannelList.size());
            return AjaxResultDTO.success(payChannelList);
        }catch (DyMallException e) {
            log.error("支付通道异常｛｝",e.getMessage(),e);
            throw new DyMallException(e.getMessage());
        }catch (Exception e) {
            log.error("支付通道异常｛｝",e.getMessage(),e);
            throw new DyMallException("系统异常，请联系客服");
        }
    }
}
