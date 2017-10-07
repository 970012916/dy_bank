package com.dayuanit.dymall.enums;

import com.dayuanit.dymall.exception.DyMallException;

public enum PayEnum {
    Alipay(1,"支付宝"),WeChatPay(2,"微信支付"),Unionpay(3,"银联支付"),CASHPAY(4,"新浪支付");
    private int code;
    private String value;
    public int getCode() {
        return code;
    }
    public String getValue() {
        return value;
    }

    PayEnum(int code,String value) {
        this.code = code;
        this.value = value;
    }

    public static PayEnum getPayEnum(int code) {
        for(PayEnum payEnum : PayEnum.values()) {
            if(payEnum.getCode() == code) {
                return payEnum;
            }
        }
        throw new DyMallException("没有找到合适的支付通道");
    }
}

