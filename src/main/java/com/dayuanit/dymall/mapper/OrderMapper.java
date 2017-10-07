package com.dayuanit.dymall.mapper;

import com.dayuanit.dymall.domain.Order;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {

    int addOrder(Order order);
    Order getOrder(int id);
    List<Order> listOrder(int userId);
    Order getOrder4Lock(int id);
    int updateOrder(Order order);
    List<Order> listOrderByStatusAndUserId(@Param("userId") Integer userId,@Param("orderStatus") Integer orderStatus,@Param("offNum") int offNum,@Param("perNum") int perNum);
    int countOrderList(@Param("userId") Integer userId,@Param("orderStatus") Integer orderStatus);
    int changeOrderStatus(@Param("status") Integer orderStatus,@Param("orderId") Integer orderId);

}
