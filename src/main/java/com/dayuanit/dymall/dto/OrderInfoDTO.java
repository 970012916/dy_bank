package com.dayuanit.dymall.dto;

import java.util.List;

import com.dayuanit.dymall.domain.OrderDetail;

public class OrderInfoDTO {
	private int id;
	private String createTime;
	private String status;
	private String userRealName;
	private String payChannel;
	private String totalPrice;
	private List<OrderDetail> goods;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserRealName() {
		return userRealName;
	}
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	
	public List<OrderDetail> getGoods() {
		return goods;
	}
	public void setGoods(List<OrderDetail> goods) {
		this.goods = goods;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	
}
