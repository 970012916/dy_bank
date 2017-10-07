package com.dayuanit.dymall.enums;

import com.dayuanit.dymall.exception.DyMallException;

public enum GoodsEnum {

	NORMAL_STATUS(1,"正常"),UNNORMAL_STATUS(2,"下架");
	private int code;
	private String value;
	public int getCode() {
		return code;
	}
	public String getValue() {
		return value;
	}
	
	 GoodsEnum(int code,String value) {
		this.code = code;
		this.value = value;
	}
	
	public static GoodsEnum getGoodsEnum(int code) {
		for(GoodsEnum goodsEnum : GoodsEnum.values()) {
			if(goodsEnum.getCode() == code) {
				return goodsEnum;
			}
		}
		throw new DyMallException("没有找到合适的商品状态");
	}
}
