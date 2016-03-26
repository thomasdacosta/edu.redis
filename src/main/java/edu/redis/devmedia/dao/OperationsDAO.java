package edu.redis.devmedia.dao;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;

import edu.redis.devmedia.object.Hero;
import edu.redis.devmedia.object.SerializeObject;
import edu.redis.devmedia.object.SimpleObject;

public class OperationsDAO extends RedisDAO {
	
	private Gson gson = new Gson();
	
	public OperationsDAO() {
		super();
	}

	/**
	 * Operações simples com get e set
	 */
	public void basicOperations() {
		jedis().set("hero:batman", UUID.randomUUID().toString());
		System.out.println(jedis().get("hero:batman"));
		
		for (int i=1;i<=10;i++) {
			jedis().incr("hero:superman");	
		}
		
		System.out.println(jedis().get("hero:superman"));
		System.out.println(jedis().exists("hero:wonderwoman"));
		System.out.println(jedis().exists("hero:batman"));
	}
	
	/**
	 * Serializando e deserializando um objeto dentro do redis
	 */
	public void serializeDeserializeOperations() {
		SimpleObject object = new SimpleObject();
		object.setNameObject("Captião América");
		object.setNumDoubleObject(50765.98);
		object.setNumIntObject(75);
		
		byte[] bytes = SerializeObject.serializeObject(object);
		jedis().set("marvel:capitao".getBytes(), bytes);
		
		SimpleObject simpleObject = (SimpleObject) SerializeObject
				.deserializeObject(jedis().get("marvel:capitao".getBytes()));
		System.out.println(simpleObject);
	}
	
	/**
	 * Operações com Hashes
	 */
	public void hashesOperations() {
		String keyMarvel = "marvel:ironman";
		jedis().hset(keyMarvel, "name", "Iron Man");
		jedis().hset(keyMarvel, "identity", "Tony Stark");
		jedis().hset(keyMarvel, "power", "Bilionare");
		
		String keyDc = "dc:batman";
		jedis().hset(keyDc, "name", "Batman");
		jedis().hset(keyDc, "identity", "Bruce Wayne");
		jedis().hset(keyDc, "power", "Bilionare");
		
		Map<String, String> heroesMarvel = jedis().hgetAll(keyMarvel);
		for (String keys : heroesMarvel.keySet()) {
			System.out.println(heroesMarvel.get(keys));
		}
		
		Map<String, String> heroesDc = jedis().hgetAll(keyDc);
		for (String keys : heroesDc.keySet()) {
			System.out.println(heroesDc.get(keys));
		}
	}
	
	/**
	 * Operações com listas
	 */
	public void listOperations() {
		String keyAvengers = "marvel:avengers";
		
		Hero heroIroMan = new Hero();
		heroIroMan.setAge(35);
		heroIroMan.setCompany("Marvel");
		heroIroMan.setIdentity("Tony Stark");
		heroIroMan.setName("IRON MAN");
		
		Hero heroCaptain = new Hero();
		heroCaptain.setAge(75);
		heroCaptain.setCompany("Marvel");
		heroCaptain.setIdentity("Steve Rogers");
		heroCaptain.setName("CAPTAIN AMERICA");
		
		long totalElements = jedis().llen(keyAvengers);
		System.out.println("Total elements in list:" + totalElements);
		
		for (int i=1;i<=totalElements;i++) {
			System.out.println("Element removed:" + jedis().lpop(keyAvengers));	
		}
		
		jedis().rpush(keyAvengers, gson.toJson(heroCaptain), gson.toJson(heroIroMan));
		jedis().lpush(keyAvengers, "Thor", "Hulk");
		jedis().rpush(keyAvengers, "Spider-Man", "Hawkeye");
		
		List<String> listAvengers = jedis().lrange(keyAvengers, 0, totalElements);
		for (String elements : listAvengers) {
			System.out.println("Hero:" + elements);
		}
	}
	
}
