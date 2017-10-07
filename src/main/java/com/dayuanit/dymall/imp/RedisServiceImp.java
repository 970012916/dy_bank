package com.dayuanit.dymall.imp;

import com.alibaba.fastjson.JSON;
import com.dayuanit.dymall.dao.RedisServiceDao;
import com.dayuanit.dymall.mapper.AddressMapper;
import com.dayuanit.dymall.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RedisServiceImp implements RedisServiceDao {


    @Autowired
    private AddressMapper addressMapper;

    private static  final Logger log = LoggerFactory.getLogger(RedisServiceImp.class);


    @Override
    public boolean hasKey(String key) {
        Jedis jedis = RedisUtil.getJedis();
        boolean flag = false;
        try {
            jedis = RedisUtil.getJedis();
            flag = jedis.exists(key);

        } finally {
            if(null == jedis) {
                jedis.close();
            }
        }
        return flag;
    }

    @Override
    public List<Map<String, String>> listAddress(String key) {
        Jedis jedis = null;
 
        try {
            jedis = RedisUtil.getJedis();
            String value = jedis.get(key);
            List addressList = JSON.parseArray(value,Map.class);
            return addressList;
        } finally {
            if(null != jedis) {
                jedis.close();
            }
        }
    }

    @Override
    public void setAddress(String key, List<Map<String, String>> value) {
        Jedis jedis = null;

        try {
            jedis = RedisUtil.getJedis();
            String valueStr = JSON.toJSONString(value);
            jedis.set(key,valueStr);
        } finally {
            if(null != jedis) {
                jedis.close();
            }
        }
    }

	@Override
	public String popOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveCartId(List<Integer> cartIds, int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Integer> getCartId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delKey(String key) {
		// TODO Auto-generated method stub
		
	}




    /*
    @Override
    public List<Map<String, String>> listCity(String key){
        Jedis jedis = null;

        try {
            jedis = RedisUtil.getJedis();
            String value = jedis.get(key);
            List provinceList = JSON.parseArray(value,Map.class);
            return provinceList
        } finally {
            if(null != jedis) {
                jedis.close();
            }
        }

    @Override
    public List<Map<String, String>> listArea(String cityCode) {
        return null;
    }*/
}
