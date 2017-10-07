package com.dayuanit.dymall.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dayuanit.dymall.dao.OrderServiceDao;
import com.dayuanit.dymall.domain.Goods;
import com.dayuanit.dymall.domain.Order;
import com.dayuanit.dymall.domain.OrderDetail;
import com.dayuanit.dymall.enums.OrderStatusEnum;
import com.dayuanit.dymall.mapper.OrderDetailMapper;
import com.dayuanit.dymall.mapper.OrderMapper;

@Component("rollbackRepertory")
public class RollBackRepertory {

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	
	@Autowired
	private OrderServiceDao orderServiceDao;
	
	private static Logger logger = LoggerFactory.getLogger(RollBackRepertory.class);
	
	public void doTask() {
		int offNum = 0;
		while(true) {
			logger.info("开始查询未支付的订单");
			List<Order> list = orderMapper.listOrderByStatusAndUserId(null, OrderStatusEnum.READYPAY.getCode(), offNum, 20);
			if(0 == list.size()) {
				break;
			}
			process(list);
			offNum = offNum + 20;
		}
	}
	
	
	public void process(List<Order> list) {
		
		for(Order order : list) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(order.getModifyTime());
			cal.add(Calendar.MINUTE, 30);
			if(cal.getTime().after(new Date())) {
				continue;
			}
			try {
				orderServiceDao.processInvalidPay(order);
			} catch (Exception e) {
				logger.error("处理过期失效订单失败{}",e);
			}
		}
	}
}
