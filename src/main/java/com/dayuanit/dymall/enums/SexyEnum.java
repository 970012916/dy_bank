package com.dayuanit.dymall.enums;

import com.dayuanit.dymall.exception.DyMallException;

public enum SexyEnum {

	MALE("男",1),FEMALE("女",2);
	
	private int value;
	private String code;
	
	
	public int getValue() {
		return value;
	}


	public String getCode() {
		return code;
	}


	private SexyEnum(String code,int value) {
		this.code = code;
		this.value = value;
	}
	
	
	public static SexyEnum getSexyEnum(String code) {
		for(SexyEnum sexyEnum : SexyEnum.values()) {
			if(sexyEnum.getCode().equals(code)) {
				return sexyEnum;
			}
		}
		throw new DyMallException("性别有误");
	}
}
