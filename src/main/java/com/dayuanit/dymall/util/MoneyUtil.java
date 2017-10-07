package com.dayuanit.dymall.util;

import com.dayuanit.dymall.domain.OrderDetail;

import java.math.BigDecimal;
import java.util.List;

public class MoneyUtil {

    public static String mul(String amount,String price){
        BigDecimal bd1 = new BigDecimal(amount);
        BigDecimal bd2 = new BigDecimal(price);
        String oneGoodsTotalPrice = bd1.multiply(bd2).setScale(2,BigDecimal.ROUND_HALF_EVEN ).toString();

        return oneGoodsTotalPrice;
    }

    public static String plus(String num1,String num2) {
        
        BigDecimal bd1 = new BigDecimal(num1);
        BigDecimal bd2 = new BigDecimal(num2);
        String totalPrice = bd1.add(bd2).setScale(2,BigDecimal.ROUND_HALF_EVEN ).toString();
        return totalPrice;
    }




}
