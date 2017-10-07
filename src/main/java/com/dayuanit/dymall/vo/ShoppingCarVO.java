package com.dayuanit.dymall.vo;

public class ShoppingCarVO {

	private Integer goodsId;
	private Integer goodsAmout;
	private String cartToken;
	
	
	
	public String getCartToken() {
		return cartToken;
	}
	public void setCartToken(String cartToken) {
		this.cartToken = cartToken;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	
	public Integer getGoodsAmout() {
		return goodsAmout;
	}
	
	public void setGoodsAmout(Integer goodsAmout) {
		this.goodsAmout = goodsAmout;
	} 
	
	
}
