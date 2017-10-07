package com.dayuanit.dymall.util;

import com.dayuanit.dymall.exception.DyMallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverterUtil implements Converter<String,Date> {

    private static final Logger log = LoggerFactory.getLogger(DateConverterUtil.class);

    @Override
    public Date convert(String s) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       try {
           dateFormat.format(s);
       } catch (DyMallException e) {
           log.info("转换日期失败{}" ,e.getMessage(),e);
           throw new DyMallException("转换日期失败");
       }

        return null;
    }
}
