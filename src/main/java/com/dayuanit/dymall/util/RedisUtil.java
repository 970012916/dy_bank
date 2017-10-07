package com.dayuanit.dymall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    private static final JedisPool pool = new JedisPool(new JedisPoolConfig(),"192.168.31.207");

    private static Logger log = LoggerFactory.getLogger(RedisUtil.class);


    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void setValue(String key,String value) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.set(key,value);
        }finally {
            if(null != jedis) {
                jedis.close();
            }
        }
    }

    public static String getValue(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            return jedis.get(key);
        }finally {
            if(null != jedis) {
                jedis.close();
            }
        }
    }
}
