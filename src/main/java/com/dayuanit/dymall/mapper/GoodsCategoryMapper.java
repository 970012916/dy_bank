package com.dayuanit.dymall.mapper;

import java.util.List;

import com.dayuanit.dymall.domain.GoodsCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsCategoryMapper {

	//GoodsCategory getCategoryStatus(int id);
	int addGoodsCategory(GoodsCategory goodsCategory);
	GoodsCategory getCategoryStatus(String categoryName);
	List<GoodsCategory> getCategory(int status);
}
