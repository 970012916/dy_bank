package com.dayuanit.dymall.imp;

import com.dayuanit.dymall.dao.GoodsServiceDao;
import com.dayuanit.dymall.domain.Goods;
import com.dayuanit.dymall.domain.GoodsCategory;
import com.dayuanit.dymall.domain.GoodsImg;
import com.dayuanit.dymall.enums.GoodsEnum;
import com.dayuanit.dymall.exception.DyMallException;
import com.dayuanit.dymall.mapper.GoodsCategoryMapper;
import com.dayuanit.dymall.mapper.GoodsImgMapper;
import com.dayuanit.dymall.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class GoodsServiceImp implements GoodsServiceDao{

	private final int CATEGORYSTATUS = 1;
	
	@Autowired
	private GoodsImgMapper goodsImgMapper;
	@Autowired
	private GoodsCategoryMapper goodsCatetoryMapper;
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Override
	public List<Goods> listGoods(int categoryId) {

		List<Goods> goodsList = goodsMapper.getGoods(categoryId);
	
		if(null == goodsList) {
			throw new DyMallException("没有找到相应的商品");
		}
		return goodsList;
	}

	@Override
	public GoodsImg getImg(Integer id) {
		GoodsImg goodsImg = goodsImgMapper.getGoodsImg(id);
		if(null == goodsImg) {
			throw new DyMallException("找不到相应的图片");
		}
		return goodsImg;
	}

	@Override
	public List<GoodsCategory> listGoodsCategory() {
		List<GoodsCategory> categoryList = goodsCatetoryMapper.getCategory(CATEGORYSTATUS);
		if(0 == categoryList.size()) {
			throw new DyMallException("没有可用的类目");
		}
		return categoryList;
	}

	@Override
	public Goods getOneGoods(int id) {
		Goods goods = goodsMapper.getOneGoods(id);
		if(0 == goods.getRepertory()) {
			throw new DyMallException("商品已经售罄");
		}
		if( GoodsEnum.UNNORMAL_STATUS.getCode() == goods.getStatus()) {
			throw new DyMallException("商品已经" + GoodsEnum.UNNORMAL_STATUS.getValue());
		}
		
		return goods;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void minusRepertory(int goodsId, int amount) {
		
		Goods goods = goodsMapper.getOneGoods4Lock(goodsId);
		if(null == goods) {
			throw new DyMallException("商品不存在或者已经下架");
		}
		
		if(goods.getRepertory()<amount) {
			throw new DyMallException("库存不足");
		}
		
		int row = goodsMapper.updateRepertory(-amount, goodsId);
		if(1 != row ) {
			throw new DyMallException("减库存失败");
		}
	}

}
