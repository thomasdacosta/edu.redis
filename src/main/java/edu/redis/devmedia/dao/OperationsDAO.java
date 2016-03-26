package edu.redis.devmedia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.gson.Gson;

import edu.redis.devmedia.object.Hero;
import edu.redis.devmedia.object.SerializeObject;
import edu.redis.devmedia.object.SimpleObject;
import redis.clients.jedis.Transaction;

public class OperationsDAO extends RedisDAO {
	
	private Gson gson = new Gson();
	
	public OperationsDAO() {
		super();
	}
	
	public void clear() {
		System.out.println("Total Keys:" + jedis().dbSize());
		jedis().move("hero:batman", 3);
		jedis().flushDB();
	}

	/**
	 * Operações simples com get, set, expire e ttl 
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
		
		jedis().set("hero:wonderwoman", "hero");
		jedis().expire("hero:wonderwoman", 5);
		
		for (int i=1;i<=6;i++) {
			System.out.println(jedis().ttl("hero:wonderwoman"));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		jedis().mset("value:1", "1", "value:2", "2", "value:3","3");
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
		jedis().hset(keyMarvel, "power", "Armor");
		
		String keyDc = "dc:batman";
		jedis().hset(keyDc, "name", "Batman");
		jedis().hset(keyDc, "identity", "Bruce Wayne");
		jedis().hset(keyDc, "power", "Bilionare");
		
		Map<String, String> heroesMarvel = jedis().hgetAll(keyMarvel);
		for (String keys : heroesMarvel.keySet()) {
			System.out.println("Fields:" + heroesMarvel.get(keys));
		}

		jedis().hdel(keyDc, "power", "identity");
		
		Map<String, String> heroesDc = jedis().hgetAll(keyDc);
		for (String keys : heroesDc.keySet()) {
			System.out.println("Fields:" + heroesDc.get(keys));
		}
		
		Map<String, String> fields = new HashMap<>();
		fields.put("identity", "Bruce Wayne");
		fields.put("power", "Only Batman");
		jedis().hmset(keyDc, fields);
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
		
		totalElements = jedis().llen(keyAvengers);
		List<String> listAvengers = jedis().lrange(keyAvengers, 0, totalElements);
		for (String elements : listAvengers) {
			System.out.println("Hero:" + elements);
		}
	}
	
	/**
	 * Operações com conjuntos.
	 * - Em conjuntos não existe valor repetido
	 * - Podem ser ordenados
	 * - Armazena somente strings
	 */
	public void setOperations() {
		String newVideogames = "new:videogames";
		jedis().sadd(newVideogames, "PS2", "PS3", "PS4", "XBox One", "XBox 360");
		jedis().sadd(newVideogames, "PS1", "PS2");
		
		Set<String> values = jedis().smembers(newVideogames);
		
		for (String value : values) {
			System.out.println(value);
		}
		
		String oldVideogames = "old:videogames";
		jedis().zadd(oldVideogames, 10, "Atari");
		jedis().zadd(oldVideogames, 11, "SNES");
		jedis().zadd(oldVideogames, 12, "MegaDrive");
		
		System.out.println(jedis().sismember(newVideogames, "NES"));
	}
	
	public void transactionsOperations() {
		Transaction transaction = jedis().multi();
		try {
			boolean error = true;
			transaction.set("value:transaction:1", "1");
			transaction.set("value:transaction:2", "2");
			transaction.set("value:transaction:3", "3");
			transaction.set("value:transaction:4", "4");
			transaction.set("value:transaction:5", "5");
			
			if (error)
				throw new Exception();
			
			transaction.exec();
		} catch (Exception ex) {
			transaction.discard();
		}
	}
	
}
