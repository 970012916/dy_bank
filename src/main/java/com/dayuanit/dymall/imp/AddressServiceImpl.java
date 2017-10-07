package com.dayuanit.dymall.imp;

import com.dayuanit.dymall.dao.AddressServiceDao;
import com.dayuanit.dymall.dao.RedisServiceDao;
import com.dayuanit.dymall.domain.Address;
import com.dayuanit.dymall.exception.DyMallException;
import com.dayuanit.dymall.mapper.AddressDetailMapper;
import com.dayuanit.dymall.mapper.AddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Service
public class AddressServiceImpl implements AddressServiceDao {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private AddressDetailMapper addressDetailMapper;

    @Resource(name="redisTemplateServiceImp")
    private RedisServiceDao redisServiceDao;

    private static  final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Override
    public List<Map<String,String>> listProvince() {
        String key = "mall:province";
        if(redisServiceDao.hasKey(key)) {
            return redisServiceDao.listAddress(key);
        }
        List<Map<String, String>> provinceList = addressMapper.listProvince();
        System.out.println("map " +provinceList.get(1));
        redisServiceDao.setAddress(key,provinceList);
        return provinceList;
    }

    @Override
    public List<Map<String, String>> listCity(String provinceCode) {
        String key = String.format("mall:city:%s",provinceCode);
        if(redisServiceDao.hasKey(key)) {
            return redisServiceDao.listAddress(key);
        }
        List<Map<String, String>> cityList = addressMapper.listCity(provinceCode);
        redisServiceDao.setAddress(key,cityList);
        return cityList;
    }

    @Override
    public List<Map<String, String>> listArea(String cityCode) {
        String key = String.format("mall:city:%s",cityCode);
        if(redisServiceDao.hasKey(key)) {
            return redisServiceDao.listAddress(key);
        }
        List<Map<String, String>> areaList = addressMapper.listArea(cityCode);
        redisServiceDao.setAddress(key,areaList);
        return areaList;
    }

    @Override
    public void addAddress(Address address) {
       int row  = addressDetailMapper.addAddress(address);
       if(1 != row ) {
           throw new DyMallException("增加地址失败");
       }
    }

    @Override
    public List<Address> listAddress() {
        List<Address> addressesList = addressDetailMapper.listAddress();
        if(0 == addressesList.size()) {
            throw new DyMallException("没有地址，请增加");
        }
        return addressesList;
       
    }

    @Override
    public int updateAddress(Address address) {
        if(null == addressDetailMapper) {
            log.info("addressDetailMapper is null");
        }
        if(null == address) {
            System.out.println("address is null");
        }
        if(0 == address.getId()) {
            System.out.println("address.getId() is 0");
        }
        Address checkedAddress = addressDetailMapper.getAddress(address.getId());
        if( null == checkedAddress) {
            throw new DyMallException("找不到合适的地址信息");
        }
        int row = addressDetailMapper.updateAddress(address);
        if(1 != row ) {
            throw new DyMallException("修改地址失败");
        }
        return row;
    }

    @Override
    public int deleteAddress(int status,int userId,int addressId) {
        Address checkedAddress = addressDetailMapper.getAddress(addressId);
        if(null == checkedAddress) {
            throw new DyMallException("没有找到该地址");
        }

        if(userId != checkedAddress.getUserId()) {
            throw new DyMallException("无权删除该地址");
        }

        int row = addressDetailMapper.deleteAddress(status,addressId);
        if(1 != row ) {
            throw new DyMallException("删除地址失败");
        }
        return row;
    }
}
