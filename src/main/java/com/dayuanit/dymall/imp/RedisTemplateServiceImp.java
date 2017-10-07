package com.dayuanit.dymall.imp;

import com.dayuanit.dymall.dao.OrderServiceDao;
import com.dayuanit.dymall.dao.RedisServiceDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service("redisTemplateServiceImp")
public class RedisTemplateServiceImp implements RedisServiceDao,InitializingBean{

	/*@Autowired
	private RedisTemplate<String, String> redisTemplate;
	//ValueOperations 简单的key-value操作  redisTemplate.opersForValue()返回一个ValueOperations
	/*@Resource(name = "redisTemplate")
	private ValueOperations<String, List<Map<String, String>>> addressOper;

	@Resource(name="redisTemplate")
	private ListOperations<String, String> orderQueue;




	@Override
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public List<Map<String, String>> listAddress(String key) {
		String value =  redisTemplate.opsForValue().get(key);

		List list = JSON.parseArray(value,Map.class);
		return list;
	}


	@Override
	public void setAddress(String key, List<Map<String, String>> value) {
		String json = JSON.toJSONString(value);
		redisTemplate.opsForValue().set(key, json);
	}

	@Override
	public String popOrder() {
		return null;
	}*/

	private final static Logger logger = LoggerFactory.getLogger(RedisTemplateServiceImp.class);

	@Autowired
	private RedisTemplate<String,Object> redisTemplate;

	
	@Resource(name="redisTemplate")
	private ValueOperations<String, List<Map<String, String>>> addressOper;
	
	
	@Autowired
	private OrderServiceDao orderServiceDao;

	@Resource(name="redisTemplate")
	private ListOperations<String, String> orderQueue;
	

	
	@Resource(name="redisTemplate")
	private SetOperations<String, Integer> cartSet;

	@Override
	public List<Map<String, String>> listAddress(String key) {
		return addressOper.get(key);
	}


	@Override
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public void setAddress(String key, List<Map<String, String>> value) {
		addressOper.set(key, value);
		
	}

	@Override
	//支付系统把支付完成情况反馈给订单系统
	public String popOrder() {
		String key = "dayuanit:pay:order";
		String orderInfo = orderQueue.rightPop(key);
		if (StringUtils.isBlank(orderInfo)) {
			try {
				//logger.info(">>>>>队列无值，我要睡觉");
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		//bizId$payId
		
		try {
			logger.info(">>>>>订单支付数据{}", orderInfo);
			String msg[] = orderInfo.split("\\$");
			String orderId = msg[0];
			String payId = msg[1];
			System.out.println("订单返回的orderId IS " + orderId);
			orderServiceDao.processPayResult(Integer.parseInt(orderId), Integer.parseInt(payId));
		} catch(Throwable e) {
			orderQueue.leftPush(key, orderInfo);
			logger.error("处理支付成功的订单失败", e);
		}
		
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					popOrder();
				}
			}
		}, "处理支付成功订单").start();
		
	}

	//存储购物车id 删除购物车
	@Override
	public void saveCartId(List<Integer> cartIds, int userId) {
		String key = "card:cach:" + userId;

		
		//为什么要使用final  jdk版本问题
	 	//为什么开启事务  没有什么作用 
		redisTemplate.execute(new SessionCallback() {
			@Override
			public Object execute(RedisOperations operations) throws DataAccessException {
				long num = operations.opsForSet().add(key, cartIds.toArray());
				operations.expire(key, 30, TimeUnit.MINUTES);
				return num;
			}

		});
	}

	@Override
	public Set<Integer> getCartId(int userId) {
		String key = "card:cach:" + userId;
		return cartSet.members(key);
	}
	
	@Override
	public void delKey(String key) {
		redisTemplate.delete(key);
	}



}
