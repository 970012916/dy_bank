package com.dayuanit.dymall.dto;

import java.util.List;

import com.dayuanit.dymall.domain.OrderDetail;

public class OrderGoodsInfDTO {
	private List<OrderDetail> list;
	private String totalGoodsPrice;
	public List<OrderDetail> getList() {
		return list;
	}
	public void setList(List<OrderDetail> list) {
		this.list = list;
	}
	public String getTotalGoodsPrice() {
		return totalGoodsPrice;
	}
	public void setTotalGoodsPrice(String totalGoodsPrice) {
		this.totalGoodsPrice = totalGoodsPrice;
	}
	
	
	

}
