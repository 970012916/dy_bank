package com.dayuanit.dymall.imp;

import com.dayuanit.dymall.dao.PayServiceDao;
import com.dayuanit.dymall.domain.PayChannel;
import com.dayuanit.dymall.exception.DyMallException;
import com.dayuanit.dymall.mapper.PayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayServiceImp implements PayServiceDao {

    @Autowired
    private PayMapper payMapper;

    @Override
    public List<PayChannel> listPayChannel() {
        List<PayChannel> payChannelList = payMapper.listPayChannel();
        if(0 == payChannelList.size()) {
            throw new DyMallException("没有支付通道");
        }
        return payChannelList;
    }
}
