package com.wjl.jedis;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

public class JedistTest {
	public static void main(String[] args) throws InterruptedException {

		Jedis jedis = new Jedis("192.168.217.128", 6379);
		jedis.auth("123456");
		// 测试redis联通性（Linux服务器必须关闭防火墙,redis必须配置该测试机的IP地址绑定或使用密码访问）
		System.out.println(jedis.ping());
		
		jedis.set("name", "wjl");
		System.out.println("name: "+jedis.get("name"));
		jedis.del("names");
		jedis.rpush("names", "wjl","dnf","bbq");
		System.out.println("names: "+jedis.lrange("names", 0, -1));
		jedis.sadd("myset", "a","b","c");
		System.out.println("myset: "+jedis.smembers("myset"));
		Map<String,String> map=new HashMap<String,String>();
		map.put("name", "wjl");
		map.put("password", "123");
		map.put("age", "27");
		jedis.hmset("user",map);
		System.out.println("user: "+jedis.hmget("user", "name","password","age"));
		
		//通常事务
		Transaction tx=jedis.multi();
		tx.set("name", "bbq");
		tx.set("age", "33");
		tx.exec();
		
		//开启对key的监控
		jedis.watch("age");
		//查询操作必须在开启事务之前，因为执行multi方法后不能再使用jedis的方法
		System.out.println("age: "+jedis.get("age"));
		//开启对key的监控后，任何其它线程对该key的修改都将导致该事务的操作无效（不会报错）
		Thread.sleep(7000);
		Transaction tx2=jedis.multi();
		tx2.incrBy("age", 10);
		//执行exec后所有监控的key都自动取消监控
		tx2.exec();
		System.out.println("added age: "+jedis.get("age"));
		
		//实际开发使用JedisPool
		JedisPool jedisPool=JedisPoolUtil.getJedisPoolInstance();
		Jedis myJedis=null;
		try {
			myJedis=jedisPool.getResource();
			myJedis.set("gender", "male");
			System.out.println("gender: "+myJedis.get("gender"));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			myJedis.close();
		}
	}
}
