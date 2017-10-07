package com.dayuanit.dymall.enums;

import com.dayuanit.dymall.exception.DyMallException;

public enum AddressStatus {

    NORMAL_STATUS(1,"正常"),UNNORMAL_STATUS(2,"不正常");

    private int code;
    private String value;

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    AddressStatus(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static AddressStatus getDefaultStatus(int code) {
        for (AddressStatus addressStatus :  AddressStatus.values()) {
            if(addressStatus.getCode() == code) {
                return addressStatus;
            }
        }

        throw new DyMallException("没有合适的地址状态");
    }
}
