package com.dayuanit.dymall.enums;

import com.dayuanit.dymall.exception.DyMallException;

public enum ShoppingCarEnum {

	ABLE(1,"正常"),UNABLE(2,"取消"),UNDERCARRIAGE(3,"商品下架");
	
	private Integer code;
	private String value;
	
	
	
	public Integer getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}


	private ShoppingCarEnum(Integer code,String value) {
		this.code = code;
		this.value = value;
	}
	
	public static ShoppingCarEnum getShoppingCarEnum(int code) {
		for(ShoppingCarEnum shoppingCarEnum : ShoppingCarEnum.values() ) {
			if(shoppingCarEnum.getCode() == code) {
				return shoppingCarEnum;
			}
		}
		throw new DyMallException("没有找到合适的购物车商品的状态");
	}
}
