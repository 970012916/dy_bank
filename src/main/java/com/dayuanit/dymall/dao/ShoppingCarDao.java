package com.dayuanit.dymall.dao;

import java.util.Map;

public interface ShoppingCarDao {

	void insertGoods(int goodsId,int userId,int amount);
	void deleteGoods(int shopCarId,int goodsid,int userId,int amount);
	Map<String,Object> listCars(int userId);
	void deleShopCar(int carId,int userId);
}
