package com.dayuanit.dymall.enums;

import com.dayuanit.dymall.exception.DyMallException;

public enum LogisticsEnum {
    sf_express(1,"顺丰"),Huitong(2,"汇通"),Yuantong(3,"圆通"),Zhongtong(4,"中通");
    private int code;
    private String value;
    public int getCode() {
        return code;
    }
    public String getValue() {
        return value;
    }

    LogisticsEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static LogisticsEnum getLogisticsEnum(int code) {
        for(LogisticsEnum logisticsEnum : LogisticsEnum.values()) {
            if(logisticsEnum.getCode() == code) {
                return logisticsEnum;
            }
        }
        throw new DyMallException("没有找到合适的订单状态");
    }
}
