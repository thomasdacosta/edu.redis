package edu.redis.devmedia.pubsub;

import edu.redis.devmedia.connection.JedisConnection;
import redis.clients.jedis.Jedis;

public class PublisherRedis {
	
	public static void main(String[] args) {
		JedisConnection jedisConnection = new JedisConnection();
		jedisConnection.init();
		Jedis jedis = jedisConnection.connection();
		
		for (int i=0;i<=100;i++) {
			jedis.publish("sales", "Sales message count " + i);	
		}
	}

}
