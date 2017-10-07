package com.dayuanit.dymall.enums;

import com.dayuanit.dymall.exception.DyMallException;

public enum IsDefaultAddress {

    DEFAULT_ADDRESS(1,"默认地址"),UNDEFAULT_ADDRESS(2,"非默认地址");

    private int code ;
    private String value;

    IsDefaultAddress(int code,String value) {
        this.code = code;
        this.value = value;

    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static IsDefaultAddress getIsDefaultAddress(int code)  {
        for(IsDefaultAddress isDefaultAddress : IsDefaultAddress.values()) {
            if(isDefaultAddress.getCode() == code) {
                return isDefaultAddress;
            }
        }
        throw new DyMallException("没有找到合适的默认地址状态");
    }
}
