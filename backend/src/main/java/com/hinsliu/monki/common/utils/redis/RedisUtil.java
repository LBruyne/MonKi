package com.hinsliu.monki.common.utils.redis;

import com.hinsliu.monki.common.UtilConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.rmi.CORBA.Util;

/**
 * @Description: utils for Redis(Jedis)
 * @author: liuxuanming
 * @date: 2021/03/28 9:58 上午
 */
@Slf4j
public class RedisUtil {

    private String server = UtilConstant.redisServer;

    private Integer port = UtilConstant.redisPort;

    // Expire at 1000 seconds later.
    private static final Integer EXPIRE_TIME = 1000;

    private static RedisUtil redisUtil;

    private static Jedis client;

    private RedisUtil() {
        try {
            client = new Jedis(server, port);
            log.info("Connect to REDIS in " + server + " " + port);
            log.info("Redis is running: " + client.ping());
        }
        catch(Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            client.close();
        }
        catch(Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    synchronized public static RedisUtil getInstance() {
        if(redisUtil == null) {
            redisUtil = new RedisUtil();
        }
        return redisUtil;
    }

    public void set(String key, String value) {
        client.setex(key, EXPIRE_TIME, value);
        log.info("REDIS写入键值对{}:{}", key, value);
    }

    public String get(String key) {
        String value = client.get(key);
        log.info("REDIS读取键值对{}:{}", key, value);
        return value;
    }

    public void remove(String key) {
        client.del(key);
        log.info("REDIS读取键{}", key);
    }
}
