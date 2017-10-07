package com.dayuanit.dymall.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisServiceDao {

    boolean hasKey(String key);
    List<Map<String, String>> listAddress(String key);

    void setAddress(String key, List<Map<String, String>> value);

    String popOrder();
    void saveCartId(List<Integer> cartIds, int userId);
    Set<Integer> getCartId(int userId);
	
	void delKey(String key);	
}
