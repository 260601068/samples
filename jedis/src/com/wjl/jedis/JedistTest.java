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
		// ����redis��ͨ�ԣ�Linux����������رշ���ǽ,redis�������øò��Ի���IP��ַ�󶨻�ʹ��������ʣ�
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
		
		//ͨ������
		Transaction tx=jedis.multi();
		tx.set("name", "bbq");
		tx.set("age", "33");
		tx.exec();
		
		//������key�ļ��
		jedis.watch("age");
		//��ѯ���������ڿ�������֮ǰ����Ϊִ��multi����������ʹ��jedis�ķ���
		System.out.println("age: "+jedis.get("age"));
		//������key�ļ�غ��κ������̶߳Ը�key���޸Ķ������¸�����Ĳ�����Ч�����ᱨ��
		Thread.sleep(7000);
		Transaction tx2=jedis.multi();
		tx2.incrBy("age", 10);
		//ִ��exec�����м�ص�key���Զ�ȡ�����
		tx2.exec();
		System.out.println("added age: "+jedis.get("age"));
		
		//ʵ�ʿ���ʹ��JedisPool
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
