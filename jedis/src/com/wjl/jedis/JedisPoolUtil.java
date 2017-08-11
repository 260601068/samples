package com.wjl.jedis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {
	private static volatile JedisPool jedisPool = null;

	private JedisPoolUtil() {
	}

	public static JedisPool getJedisPoolInstance() {
		if (jedisPool == null) {
			synchronized (JedisPoolUtil.class) {
				if (jedisPool == null) {
					JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
					jedisPoolConfig.setMaxTotal(1000);
					jedisPoolConfig.setMaxIdle(32);
					jedisPoolConfig.setMaxWaitMillis(100 * 1000);
					jedisPoolConfig.setTestOnBorrow(true);
					jedisPool = new JedisPool(jedisPoolConfig, "192.168.217.128", 6379, 3000, "123456");
				}
			}
		}
		return jedisPool;
	}
}
