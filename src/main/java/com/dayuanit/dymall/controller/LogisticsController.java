package com.dayuanit.dymall.controller;

import com.dayuanit.dymall.dao.LogisticsServiceDao;
import com.dayuanit.dymall.domain.Logistics;
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
@RequestMapping("/logistics")
public class LogisticsController {

    @Autowired
    private LogisticsServiceDao logisticsServiceDao;

    private static final Logger log = LoggerFactory.getLogger(LogisticsController.class);
    @RequestMapping("/listLogistics")
    @ResponseBody
    public AjaxResultDTO listLogistics(){
        try {
            List<Logistics> logisticsList = logisticsServiceDao.listLogistics();
            System.out.println("logisticsList size is " + logisticsList.size());

            return AjaxResultDTO.success(logisticsList);
        }catch (DyMallException e) {
            log.error("支付通道异常｛｝",e.getMessage(),e);
            throw new DyMallException(e.getMessage());
        }catch (Exception e) {
            log.error("支付通道异常｛｝",e.getMessage(),e);
            throw new DyMallException("系统异常，请联系客服");
        }
    }
}
