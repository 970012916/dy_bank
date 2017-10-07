package com.dayuanit.dymall.enums;

import com.dayuanit.dymall.exception.DyMallException;

public enum OrderStatusEnum {

    UNPAY(1,"未付款"),PAYED(2,"已付款"),INVALID(3,"失效"),CANCLE(4,"取消"),READYPAY(5,"准备付款");
    private int code;
    private String value;
    public int getCode() {
        return code;
    }
    public String getValue() {
        return value;
    }

    OrderStatusEnum(int code,String value) {
        this.code = code;
        this.value = value;
    }

    public static OrderStatusEnum getOrderStatusEnum(int code) {
        for(OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            if(orderStatusEnum.getCode() == code) {
                return orderStatusEnum;
            }
        }
        throw new DyMallException("没有找到合适的订单支付通道");
    }
}
