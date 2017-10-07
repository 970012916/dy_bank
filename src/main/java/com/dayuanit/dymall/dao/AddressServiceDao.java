package com.dayuanit.dymall.dao;

import com.dayuanit.dymall.domain.Address;

import java.util.List;
import java.util.Map;


public interface AddressServiceDao {
    List<Map<String, String>> listProvince();
    List<Map<String,String>> listCity(String provinceCode);
    List<Map<String,String>> listArea(String cityCode);
    void addAddress(Address address);
    List<Address> listAddress();
    int updateAddress(Address address);
    int deleteAddress(int status,int userId,int addressId);
}
