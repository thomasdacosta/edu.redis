package edu.redis.devmedia.dao;

import edu.redis.devmedia.connection.JedisConnection;
import redis.clients.jedis.Jedis;

public abstract class RedisDAO {

	private JedisConnection jedisConnection = null;
	private Jedis jedis = null;
	
	public RedisDAO() {
		jedisConnection = new JedisConnection();
		jedisConnection.init();
	}
	
	public Jedis jedis() {
		if (jedis == null) {
			jedis = jedisConnection.connection();
		}
		return jedis;
	}
	
}
