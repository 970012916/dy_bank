package com.dayuanit.dymall.dao;

import com.dayuanit.dymall.domain.Order;
import com.dayuanit.dymall.dto.OrderGoodsInfDTO;
import com.dayuanit.dymall.vo.OrderVO;
import com.dayuanit.dymall.vo.ShopCarOrderVO;
import com.dayuanit.pay.domain.PayResultInfo;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderServiceDao {

    Order createOrder(List<ShopCarOrderVO> list, int userId);
    PayResultInfo changeOrderStatus(OrderVO orderVO,int userId);
    PayResultInfo changeOrderStatusFromOrder(int userId,int orderId);
    OrderGoodsInfDTO listOrder(int orderId,int userId);
    Map<Object ,Object> listMyOrders(Integer userId, Integer orderStatus, Integer currentPage);
    void processPayResult(int orderId, int payId);
    void processInvalidPay(Order order);
}
