/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import redis.clients.jedis.Jedis;

/**
 *
 * @author bluez
 */
public class RedisConfig {

    private static final String HOST = "localhost";
    private static final int PORT = 6379;

    public static Jedis getJedis() {
        return new Jedis(HOST, PORT);
    }
    public static String getHost()
    {
        return HOST;
    }
    public static int getPort()
    {
        return PORT;
    }
}
