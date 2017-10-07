package com.dayuanit.dymall.vo;

import redis.clients.jedis.Jedis;

public class Test {
    public static void main(String[] args) {

        Jedis jedis = new Jedis("192.168.31.207",6379);//配置的是虚拟机的ip
        jedis.set("foo", "bar");

        String value = jedis.get("age");
        System.out.println(value);
//Jedis Cluster will attempt to discover cluster nodes automatically
    }
}
