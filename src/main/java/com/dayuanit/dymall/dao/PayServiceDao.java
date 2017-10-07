package com.dayuanit.dymall.dao;

import com.dayuanit.dymall.domain.PayChannel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayServiceDao {
    List<PayChannel> listPayChannel();
}
