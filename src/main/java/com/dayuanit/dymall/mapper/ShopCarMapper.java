package com.dayuanit.dymall.mapper;

import com.dayuanit.dymall.domain.ShoppingCar;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopCarMapper {

	int addGoods(ShoppingCar shoppingCar);
	List<ShoppingCar> listCar(int userId);
	ShoppingCar getShoppingCar(int id);
	ShoppingCar getSameShoppingCar(@Param("goodsId")int goodsId,@Param("userId")int userId);
	int updateSameCar(@Param("amount") int amount,@Param("goodsId")int goodsId,@Param("userId")int userId);
	int getCount(int userId);
	List<ShoppingCar> listShoppingCar(@Param("userId")int userId);
	int updateCar(ShoppingCar shoppingCar);
}
