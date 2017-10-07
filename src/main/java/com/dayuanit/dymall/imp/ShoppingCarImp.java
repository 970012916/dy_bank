package com.dayuanit.dymall.imp;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dayuanit.dymall.dao.ShoppingCarDao;
import com.dayuanit.dymall.domain.Goods;
import com.dayuanit.dymall.domain.ShoppingCar;
import com.dayuanit.dymall.enums.GoodsEnum;
import com.dayuanit.dymall.enums.ShoppingCarEnum;
import com.dayuanit.dymall.exception.DyMallException;
import com.dayuanit.dymall.mapper.GoodsMapper;
import com.dayuanit.dymall.mapper.ShopCarMapper;
import com.dayuanit.dymall.util.PageUtil;

@Service
public class ShoppingCarImp implements ShoppingCarDao{

	@Autowired
	private ShopCarMapper shopCarMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private PageUtil pageUtil;
	
	@Override
	public void insertGoods(int goodsId, int userId,int amount) {
		Goods goods = goodsMapper.getOneGoods(goodsId);
		
		if(GoodsEnum.UNNORMAL_STATUS.getCode() == goods.getStatus()) {
			throw new DyMallException("商品已下架");
		}
		
		if(0 == goods.getRepertory()) {
			throw new DyMallException("商品已售空");
		}
		
		ShoppingCar shoppingCar = shopCarMapper.getSameShoppingCar(goodsId, userId);
		if(null == shoppingCar) {
			shoppingCar = new ShoppingCar();
			
			shoppingCar.setGoodsAmout(amount);
			shoppingCar.setGoodsId(goodsId);
			shoppingCar.setUserId(userId);
			shoppingCar.setStatus(ShoppingCarEnum.ABLE.getCode());
			
			int row = shopCarMapper.addGoods(shoppingCar);
			if(1 != row ) {
				throw new DyMallException("增加购物车失败");
			}
		}
		if(null != shoppingCar) {
			int	row = shopCarMapper.updateSameCar(amount, goodsId, userId);
			if(1 != row ) {
				throw new DyMallException("增加购物车失败");
			}
		}
		
	}

	@Override
	public void deleteGoods(int shopCarId,int goodsId, int userId,int amount) {
		
		ShoppingCar shoppingCar = shopCarMapper.getShoppingCar(shopCarId);
		if(null == shoppingCar) {
			throw new DyMallException("商品不存在");
		}
		
		if(shoppingCar.getGoodsAmout() < amount ) {
			amount = shoppingCar.getGoodsAmout();
		}
		
		shoppingCar = new ShoppingCar();
		
		shoppingCar.setId(shopCarId);
		shoppingCar.setGoodsAmout(-amount);
		shoppingCar.setGoodsId(goodsId);
		shoppingCar.setUserId(userId);
		shoppingCar.setStatus(ShoppingCarEnum.ABLE.getCode());
		
		int row = shopCarMapper.updateCar(shoppingCar);
		if(1 != row ) {
			throw new DyMallException("更新购物车失败");
		}
		
	}

	@Override
	public Map<String,Object> listCars(int userId) {
		
		int totalNum = shopCarMapper.getCount(userId);
		if(1>totalNum) {
			throw new DyMallException("购物车没有商品");
		}

		List<ShoppingCar> carList= shopCarMapper.listShoppingCar(userId);
		if(0 == carList.size()) {
			throw new DyMallException("您的购物车已经加载完毕");
		}
		Map<String, Object> map = new HashMap<String ,Object>();
		map.put("carList", carList);
		
		return map;
	}

	@Override
	public void deleShopCar(int carId, int userId) {
		
		ShoppingCar car = shopCarMapper.getShoppingCar(carId);
		if(null == car) {
			throw new DyMallException("没有购物侧记录");
		}
		if(userId != car.getUserId()) {
			throw new DyMallException("无权删除购物车");
		}
		car.setStatus(ShoppingCarEnum.UNABLE.getCode());
		int row = shopCarMapper.updateCar(car);
		if(1 != row) {
			throw new DyMallException("删除购物车失败");
		}
	}

	
}
