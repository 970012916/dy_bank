package com.dayuanit.dymall.mapper;

import org.springframework.stereotype.Repository;

import com.dayuanit.dymall.domain.AddressSnap;

@Repository
public interface AddressSnapMapper {

	int addAddressSnap(AddressSnap addressSnap);
	AddressSnap getAddressSnap(int orderId);
}
