package com.dayuanit.dymall.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.dayuanit.dymall.domain.Goods;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsMapper {
	//List<Goods> getGoods(@Param("category")String category);
	List<Goods> getGoods(int categoryId);
	Goods getOneGoods(int id);
	int updateRepertory(@Param("repertory")int repertory,@Param("id")int id);
	Goods getOneGoods4Lock(int id);
}
