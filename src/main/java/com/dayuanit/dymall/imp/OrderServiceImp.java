package com.dayuanit.dymall.imp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dayuanit.dymall.enums.*;
import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dayuanit.dymall.dao.GoodsServiceDao;
import com.dayuanit.dymall.dao.OrderServiceDao;
import com.dayuanit.dymall.domain.Address;
import com.dayuanit.dymall.domain.AddressSnap;
import com.dayuanit.dymall.domain.Goods;
import com.dayuanit.dymall.domain.Order;
import com.dayuanit.dymall.domain.OrderDetail;
import com.dayuanit.dymall.dto.OrderGoodsInfDTO;
import com.dayuanit.dymall.dto.OrderInfoDTO;
import com.dayuanit.dymall.exception.DyMallException;
import com.dayuanit.dymall.mapper.AddressDetailMapper;
import com.dayuanit.dymall.mapper.AddressSnapMapper;
import com.dayuanit.dymall.mapper.GoodsMapper;
import com.dayuanit.dymall.mapper.OrderDetailMapper;
import com.dayuanit.dymall.mapper.OrderMapper;
import com.dayuanit.dymall.util.MoneyUtil;
import com.dayuanit.dymall.util.PageUtil;
import com.dayuanit.dymall.util.TimeUtil;
import com.dayuanit.dymall.vo.OrderVO;
import com.dayuanit.dymall.vo.ShopCarOrderVO;
import com.dayuanit.pay.domain.PayOrder;
import com.dayuanit.pay.domain.PayResultInfo;
import com.dayuanit.pay.enums.PayStatusCodeEnum;
import com.dayuanit.pay.service.PayService;

@Service("orderServiceImp")
public class OrderServiceImp implements OrderServiceDao{

	private static final Logger log = LoggerFactory.getLogger(OrderServiceImp.class);
	
	@Autowired
	private GoodsServiceDao goodsServiceDao;
	
	@Autowired
	private AddressDetailMapper addressDetailMapper;
    
	@Autowired
    private GoodsMapper goodsMapper;

	@Autowired
    private PayService payService;

    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private AddressSnapMapper addressSnapMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(List<ShopCarOrderVO> list, int userId) {

    	String allTotalPrice = "0";
    	if(0 == list.size()) {
    	    throw new DyMallException("页面信息不合法");
        }
    	List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>(list.size());

    	for(ShopCarOrderVO shopCarOrderVO :list) {

            int goodsAmount = shopCarOrderVO.getGoodsAmount();
            int goodsId = shopCarOrderVO.getGoodsId();

            Goods goods = goodsMapper.getOneGoods(goodsId);

            if (null == goods) {
                throw new DyMallException("商品不存在");
            }

            if (goodsAmount > goods.getRepertory()) {
                throw new DyMallException("库存不足");
            }

            if (goodsAmount <= 0) {
                throw new DyMallException("购买的数量必须大于一");
            }

            if (GoodsEnum.UNNORMAL_STATUS.getCode() == goods.getStatus()) {
                throw new DyMallException("商品已经下架");
            }
            String price = goods.getPrice();
            String totalPrice = MoneyUtil.mul(String.valueOf(goodsAmount), price);

            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setGoodsTotalPrice(totalPrice);
            orderDetail.setGoodsPrice(goods.getPrice());
            orderDetail.setGoodsId(goods.getId());
            orderDetail.setGoodsAmount(goodsAmount);
            orderDetail.setGoodsName(goods.getGoodsName());
            orderDetailList.add(orderDetail);
            allTotalPrice = MoneyUtil.plus(allTotalPrice, totalPrice);

        }

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(allTotalPrice);
        order.setStatus(OrderStatusEnum.UNPAY.getCode());
        order.setOrderFrom(GoodsFromEnum.FROME_CAR.getCode());

        int row = orderMapper.addOrder(order);
        if(1 != row ) {
            throw new DyMallException("生成订单失败");
        }
        for(OrderDetail orderDetail :orderDetailList) {
            orderDetail.setOrderId(order.getId());
            row = orderDetailMapper.addOrderDetail(orderDetail);
            if(1 != row ) {
                throw new DyMallException("生成详情订单失败");
            }
        }
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayResultInfo changeOrderStatus(OrderVO orderVO,int userId) {

        int orderId = orderVO.getOrderId();
        Order order = orderMapper.getOrder(orderId);
        String totalPrice = order.getTotalPrice();

        if(null == order) {
            throw new DyMallException("没有订单信息");
        }
        if(userId != order.getUserId()) {
            throw new DyMallException("订单不属于你");
        }
        if(OrderStatusEnum.INVALID.getCode() == order.getStatus()) {
            throw new DyMallException("订单已经失效");
        }
        if(OrderStatusEnum.CANCLE.getCode() == order.getStatus()) {
            throw new DyMallException("订单已经取消");
        }
        if(OrderStatusEnum.PAYED.getCode() == order.getStatus()) {
            throw new DyMallException("订单已经支付过了，不能重复支付");
        }
        
       Goods goods = null;
       List<OrderDetail> list = orderDetailMapper.listOrderDetail(orderId);
       for(OrderDetail orderDetail : list) {
    	  int goodsId = orderDetail.getGoodsId();
    	  goods = goodsMapper.getOneGoods(goodsId);

          if(null == goods) {
              throw new DyMallException("商品不存在");
          }

          if(orderDetail.getGoodsAmount() > goods.getRepertory()) {
              throw new DyMallException("库存不足");
          }

          if(orderDetail.getGoodsAmount() <=0) {
              throw new DyMallException("购买的数量必须大于一");
          }

          if(GoodsEnum.UNNORMAL_STATUS.getCode() == goods.getStatus()) {
              throw new DyMallException("商品已经下架");
          }
         
       }
        
        Calendar calendar = Calendar.getInstance();
        Date orderTime = order.getModifyTime();
        calendar.setTime(orderTime);
        calendar.add(Calendar.MINUTE,30);
        if(new Date().after(calendar.getTime())){
            order.setStatus(OrderStatusEnum.INVALID.getCode());
            throw new DyMallException("订单失效");
        }

        int row = -1;
        if(OrderStatusEnum.UNPAY.getCode() == order.getStatus()) {
            order = new Order();
            order.setId(orderId);
            order.setStatus(OrderStatusEnum.READYPAY.getCode());
            //order.setSnapId(orderVO.getAddressId());
            int addressSnapId = addAddSnap(orderVO.getAddressId(), orderId);
            order.setSnapId(addressSnapId);
            order.setPayChannel(orderVO.getPayCode());
            order.setLogisticsChannel(orderVO.getLogisticsCode());
            row = orderMapper.updateOrder(order);
            if(1 != row ) {
                throw new DyMallException("提交订单失败");
            }
        }

        //请求支付系统，获取支付地址，支付的id，是否success
        PayOrder payOrder = new PayOrder();
        payOrder.setTotalPrice(totalPrice);
        payOrder.setBankId(null);
        payOrder.setBizId(String.valueOf(orderId));
        payOrder.setDetailMsg("大猿商城");
        payOrder.setPayChannel(order.getPayChannel());
        payOrder.setUserId(userId);
        
        //map里的payOrder是不是同一个
        PayResultInfo payResultInfo = payService.addPayOrder(payOrder);
        int payId =Integer.parseInt(payResultInfo.getPayId());
        order.setPayId(payId);
        row = orderMapper.updateOrder(order);
        if(1 != row) {
        	throw new DyMallException("商品订单关联支付id失败");
        	
        }

        List<OrderDetail> detailList = orderDetailMapper.listOrderDetail(orderId);
        for(OrderDetail orderDetail : detailList) {
            goodsServiceDao.minusRepertory(orderDetail.getGoodsId(),orderDetail.getGoodsAmount());
        }

        return payResultInfo;
    }

    @Override
    public OrderGoodsInfDTO listOrder(int orderId,int userId) {
        Order order = orderMapper.getOrder(orderId);
        
        if(null == order) {
            throw new DyMallException("订单不存在");
        }
        
        String totalGoodsPrice = order.getTotalPrice();
        OrderGoodsInfDTO orderGoodsInfDTO = new OrderGoodsInfDTO();
        orderGoodsInfDTO.setTotalGoodsPrice(totalGoodsPrice);

        if(OrderStatusEnum.CANCLE.getCode() == order.getStatus() || OrderStatusEnum.INVALID.getCode() == order.getStatus()) {
            throw new DyMallException("订单已失效或者已取消");
        }
        if(userId != order.getUserId()) {
            throw new DyMallException("订单号不属于你");
        }

        List<OrderDetail> orderDetailList = orderDetailMapper.listOrderDetail(orderId);
        System.out.println("<<<<<<<<<<<  " + orderDetailList.size());
        orderGoodsInfDTO.setList(orderDetailList);
        if(0 == orderDetailList.size()) {
            throw new DyMallException("您还没有添加订单");
        }
        return orderGoodsInfDTO;
    }
    
    public int addAddSnap(int addressId,int orderId) {
    	Address address = addressDetailMapper.getAddress(addressId);
    	if(null == address) {
    		throw new DyMallException("选择的地址不存在");
    	}
    		
    	if(AddressStatus.NORMAL_STATUS.getCode() != address.getStatus()) {
    		throw new DyMallException("选取的地址不可用");
    	}
    	AddressSnap addressSnap = new AddressSnap();
    	addressSnap.setAreaCode(address.getAreaCode());
    	addressSnap.setAreaName(address.getAreaName());
    	addressSnap.setCityCode(address.getCityCode());
    	addressSnap.setCityName(address.getCityName());
    	addressSnap.setDetailAddress(address.getDetailAddress());
    	addressSnap.setMobileNum(address.getMobileNum());
    	addressSnap.setOrderId(orderId);
    	addressSnap.setProvinceCode(address.getProvinceCode());
    	addressSnap.setProvinceName(address.getProvinceName());
    	addressSnap.setUsername(address.getUsername());
    	int row = addressSnapMapper.addAddressSnap(addressSnap);
    	if(1 != row) {
    		throw new DyMallException("地址快照生成失败");
    	}
    	return addressSnap.getId();
    }

	@Override
	public Map<Object ,Object> listMyOrders(Integer userId,  Integer orderStatus,Integer currentPage) {
        Map<Object ,Object> map = new HashMap<Object ,Object>();
        
        if(orderStatus < 0) {
			orderStatus = null;
		}
        if(currentPage <= 0 ) {
            throw new DyMallException("页码不合法");
        }
        
        int totalNum = orderMapper.countOrderList(userId,orderStatus);
        int totalPageNum = PageUtil.gettotalPageNum(totalNum);
        map.put("totalPageNum",totalPageNum);
    
        int offNum = PageUtil.getOffNum(currentPage);
        map.put("currentPage",currentPage);

        System.out.println("orderStatus is " + orderStatus);
		List<Order> orderList = orderMapper.listOrderByStatusAndUserId(userId, orderStatus,offNum,PageUtil.PER_NUM);
		List<OrderInfoDTO> list = new ArrayList<OrderInfoDTO>(orderList.size());
		if(0 == orderList.size()) {
			throw new DyMallException("您还没有提交过订单");
		}
		for(Order order : orderList) {
			
			OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
			orderInfoDTO.setTotalPrice(order.getTotalPrice());
			orderInfoDTO.setCreateTime(TimeUtil.dateToString(order.getModifyTime()));
			int orderId = order.getId();
			List<OrderDetail> detailList = orderDetailMapper.listOrderDetail(orderId);
			orderInfoDTO.setGoods(detailList);
			orderInfoDTO.setId(order.getId());
			
			
			int payChannel = order.getPayChannel();
			orderInfoDTO.setPayChannel(PayEnum.getPayEnum(payChannel).getValue());
			
			int status = order.getStatus();
			orderInfoDTO.setStatus(OrderStatusEnum.getOrderStatusEnum(status).getValue());
			AddressSnap addressSnap = addressSnapMapper.getAddressSnap(orderId);
			orderInfoDTO.setUserRealName(addressSnap.getUsername());
			list.add(orderInfoDTO);
			
		}
        map.put("list",list);
		return map;
	}

	//没有userId是否会有安全隐患，加上事务的作用处理释放悲观锁，还有什么作用？在定时器扫描改订单状态为失败的时候起作用
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void processPayResult(int orderId, int payId) {
		Order order = orderMapper.getOrder4Lock(orderId);
		if(null == order) {
			return;
		}
		
		if(order.getStatus() != OrderStatusEnum.READYPAY.getCode()) {
			return;
		}
		
		int row = orderMapper.changeOrderStatus(OrderStatusEnum.PAYED.getCode(), orderId);
		if(1 != row) {
			throw new DyMallException("订单更新失败");
		}
		log.info(">>>>>>订单{}支付成功，请及时发货", orderId);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public PayResultInfo changeOrderStatusFromOrder(int userId, int orderId) {
		Order order = orderMapper.getOrder(orderId);
        
		if(null == order) {
            throw new DyMallException("没有订单信息");
        }
        if(userId != order.getUserId()) {
            throw new DyMallException("订单不属于你");
        }
        if(OrderStatusEnum.READYPAY.getCode() != order.getStatus()) {
            throw new DyMallException("订单不能支付");
        }
    
        String totalPrice = order.getTotalPrice();
     
        //不需要判断失效了
        Calendar calendar = Calendar.getInstance();
        Date orderTime = order.getModifyTime();
        calendar.setTime(orderTime);
        calendar.add(Calendar.MINUTE,30);
        if(new Date().after(calendar.getTime())){
            order.setStatus(OrderStatusEnum.INVALID.getCode());
            throw new DyMallException("订单失效");
        }

        //请求支付系统，获取支付地址，支付的id，是否success
        System.out.println("ddddddddd" + totalPrice);
        PayOrder payOrder = new PayOrder();
        payOrder.setTotalPrice(totalPrice);
        payOrder.setBankId(null);
        payOrder.setBizId(String.valueOf(orderId));
        payOrder.setDetailMsg("大猿商城");
        payOrder.setPayChannel(order.getPayChannel());
        payOrder.setUserId(userId);
        //map里的payOrder是不是同一个
        PayResultInfo payResultInfo = payService.addPayOrder(payOrder);
        if(!payResultInfo.isSuccess()) {
        	throw new DyMallException("处理支付订单异常");
        }
        
        if(payResultInfo.getCode() == PayStatusCodeEnum.ALREADY_PAID.getCode()) {
        	processPayResult(orderId, Integer.parseInt(payResultInfo.getPayId()));
        	return payResultInfo;
        }
        
        if(payResultInfo.getCode() == PayStatusCodeEnum.PAY_INVALID.getCode()) {
        	throw new DyMallException("支付订单失效");
        }
        
        int payId =Integer.parseInt(payResultInfo.getPayId());
        order.setPayId(payId);
        int row = orderMapper.updateOrder(order);
        if(1 != row) {
        	throw new DyMallException("商品订单关联支付id失败");
        }
        
      // goodsServiceDao.minusRepertory(goodsId, amount);
        return payResultInfo;
	}

	//增加事务防止正常处理支付请求的程序产生并发
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void processInvalidPay(Order order) {
		
		order  = orderMapper.getOrder4Lock(order.getId());
		if(null == order) {
			throw new DyMallException("订单不存在");//防止有傻逼直接操纵数据库删掉了订单报空指针
		}
		
		if(OrderStatusEnum.READYPAY.getCode() != order.getStatus()) {
			throw new DyMallException("无法修改订单状态");
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(order.getModifyTime());
		cal.add(Calendar.MINUTE, 30);
		
		if (cal.getTime().after(new Date())) {
			return;
		}
		int orderId = order.getId();
		int row = orderMapper.changeOrderStatus(OrderStatusEnum.INVALID.getCode(),orderId);
		if(1 != row) {
			throw new DyMallException("更新过期订单失败");
		}

		List<OrderDetail> list = orderDetailMapper.listOrderDetail(orderId);
		for(OrderDetail od : list) {
			row = goodsMapper.updateRepertory(od.getGoodsAmount(), od.getGoodsId());
			if(1 != row) {
				throw new DyMallException("回滚库存失败");
			}
			log.info(">>>>>>>>商品{}增加数量{}", od.getGoodsId(), od.getGoodsAmount());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}