package com.dayuanit.dymall.mapper;

import com.dayuanit.dymall.domain.GoodsImg;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsImgMapper {
	
	//@Param("imgName")String imgName,
	GoodsImg getGoodsImg(int id);
}
