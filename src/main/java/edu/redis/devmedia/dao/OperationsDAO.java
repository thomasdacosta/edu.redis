package edu.redis.devmedia.dao;

import java.util.Map;
import java.util.UUID;

import edu.redis.devmedia.object.SerializeObject;
import edu.redis.devmedia.object.SimpleObject;

public class OperationsDAO extends RedisDAO {
	
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
		String keyJla = "dc:justiceleague";
		String keyAvengers = "marvel:avengers";
		
		
	}
	
}
