package com.dayuanit.dymall.domain;

import java.util.Date;

public class Order {

	private Integer id;
	private Integer userId;
	private String totalPrice;
	private Integer status;
	private String remark;
	private Integer orderFrom;
	private Date createTime;
	private Date modifyTime;
	private Integer snapId;
	private Integer payChannel;
	private Integer logisticsChannel;
	private Integer payId;
	

	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}

	public Integer getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}

	public Integer getLogisticsChannel() {
		return logisticsChannel;
	}

	public void setLogisticsChannel(Integer logisticsChannel) {
		this.logisticsChannel = logisticsChannel;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getOrderFrom() {
		return orderFrom;
	}
	public void setOrderFrom(Integer orderFrom) {
		this.orderFrom = orderFrom;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getSnapId() {
		return snapId;
	}
	public void setSnapId(Integer snapId) {
		this.snapId = snapId;
	}
	
	
	
}
