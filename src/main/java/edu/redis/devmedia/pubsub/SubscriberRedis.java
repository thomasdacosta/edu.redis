package edu.redis.devmedia.pubsub;

import edu.redis.devmedia.connection.JedisConnection;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class SubscriberRedis extends JedisPubSub {
	
	private JedisConnection jedisConnection = new JedisConnection();
	
	public static void main(String[] args) {
		System.out.println("SubscriberRedis Start");
		SubscriberRedis subscriberRedis = new SubscriberRedis();
		subscriberRedis.subscribe();
	}
	
	public void subscribe() {
		jedisConnection.init();
		Jedis jedis = jedisConnection.connection();
		jedis.subscribe(this, "sales");
	}
	
	@Override
	public void onMessage(String channel, String message) {
		System.out.println("Channel:" + channel + " Message:" + message);
	}
	
	@Override
	public void onPMessage(String pattern, String channel, String message) {
		super.onPMessage(pattern, channel, message);
	}
	
	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		super.onPSubscribe(pattern, subscribedChannels);
	}
	
	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		super.onPUnsubscribe(pattern, subscribedChannels);
	}
	
	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		super.onSubscribe(channel, subscribedChannels);
	}
	
	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		super.onUnsubscribe(channel, subscribedChannels);
	}

}
