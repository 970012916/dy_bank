package com.dayuanit.dymall.mapper;

import com.dayuanit.dymall.domain.Address;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDetailMapper {
    int addAddress(Address address);
    List<Address> listAddress();
    int updateAddress(Address address);
    Address getAddress(int id);
    int deleteAddress(@Param("status") int status,@Param("addressId") int addressId);
}
