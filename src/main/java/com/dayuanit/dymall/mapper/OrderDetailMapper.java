package com.dayuanit.dymall.mapper;

import com.dayuanit.dymall.domain.OrderDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailMapper {


    int addOrderDetail(OrderDetail orderDetail);
    List<OrderDetail> listOrderDetail(int orderId);
}
