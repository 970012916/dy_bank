package com.dayuanit.dymall.enums;

import com.dayuanit.dymall.exception.DyMallException;

public enum GoodsFromEnum {

    FROME_CAR(1,"购物车"),FROM_GOODSBREIF(2,"商品详情");

    private int code;
    private String value;

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }


    GoodsFromEnum(int code,String value){
        this.code = code;
        this.value = value;
    }


    public static GoodsFromEnum getGoodsFromEnum(int code) {
        for(GoodsFromEnum goodsFromEnum : GoodsFromEnum.values()) {
            if(goodsFromEnum.getCode() == code) {
                return goodsFromEnum;
            }
        }
        throw new DyMallException("没有找到合适的订单来源");
    }
}
