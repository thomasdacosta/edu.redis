package edu.redis.devmedia.connection;

import java.net.URI;
import java.net.URISyntaxException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Classe de conexão com o Redis utilizando API Jedis
 * 
 * - Redis sempre grava em bytes as chaves e os valores
 * 
 * @author thomasdacosta
 *
 */
public class JedisConnection {

	private JedisPool jedisPool = null;
	
	public JedisConnection() {
	}
	
	public void init() {
		URI uri = null;
		try {
			uri = new URI("redis",null,"localhost",6379,String.format("/%s", 1),null,null);
			jedisPool = new JedisPool(uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public Jedis connection() {
		return jedisPool.getResource();
	}
	
}
