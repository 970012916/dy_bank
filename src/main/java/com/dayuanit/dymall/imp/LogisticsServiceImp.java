package com.dayuanit.dymall.imp;

import com.dayuanit.dymall.dao.LogisticsServiceDao;
import com.dayuanit.dymall.domain.Logistics;
import com.dayuanit.dymall.exception.DyMallException;
import com.dayuanit.dymall.mapper.LogisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LogisticsServiceImp implements LogisticsServiceDao {

    @Autowired
    private LogisticsMapper logisticsMapper;

    @Override
    public List<Logistics> listLogistics() {
        List<Logistics> logisticsList = logisticsMapper.listLogistics();
        if(0 == logisticsList.size()) {
            throw new DyMallException("没有物流通道");
        }
        return logisticsList;
    }
}
