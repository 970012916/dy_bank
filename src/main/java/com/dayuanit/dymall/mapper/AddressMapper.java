package com.dayuanit.dymall.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface AddressMapper {
    List<Map<String,String>> listProvince();
    List<Map<String ,String >> listCity(String provinceCode);
    List<Map<String,String>> listArea(String cityCode);

}
