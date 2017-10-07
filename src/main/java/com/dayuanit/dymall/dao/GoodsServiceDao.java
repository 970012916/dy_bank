package com.dayuanit.dymall.dao;

import java.util.List;

import com.dayuanit.dymall.domain.Goods;
import com.dayuanit.dymall.domain.GoodsCategory;
import com.dayuanit.dymall.domain.GoodsImg;

public interface GoodsServiceDao {

	List<Goods> listGoods(int categoryId);
	GoodsImg getImg(Integer id);
	List<GoodsCategory> listGoodsCategory();
	Goods getOneGoods(int id);
	void minusRepertory(int goodsId,int amount);
}
