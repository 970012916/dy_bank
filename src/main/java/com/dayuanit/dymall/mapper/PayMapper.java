package com.dayuanit.dymall.mapper;

import com.dayuanit.dymall.domain.PayChannel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayMapper {
    List<PayChannel> listPayChannel();
    PayChannel getPayChannel(int id);
}
