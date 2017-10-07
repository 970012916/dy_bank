package com.dayuanit.dymall.vo;

public class OrderVO {

    private Integer orderId;
    private Integer payCode;
    private Integer logisticsCode;
    private Integer addressId;
	
    public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getPayCode() {
		return payCode;
	}
	public void setPayCode(Integer payCode) {
		this.payCode = payCode;
	}
	public Integer getLogisticsCode() {
		return logisticsCode;
	}
	public void setLogisticsCode(Integer logisticsCode) {
		this.logisticsCode = logisticsCode;
	}
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
    
    
}
