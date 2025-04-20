/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package springapp.web.controller;

import redis.clients.jedis.Jedis;
import until.RedisConfig;

/**
 *
 * @author Bluez
 */
public class TestJedis {
    public static void main(String[] args) {
        try(Jedis jedis = RedisConfig.getJedis())
        {
            jedis.set("case", "hello");
            System.out.println(jedis.get("case"));
        }
        
    }
}
