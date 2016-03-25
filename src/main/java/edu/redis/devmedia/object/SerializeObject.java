package edu.redis.devmedia.object;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeObject {
	
	public static byte[] serializeObject(Object object) {
		ByteArrayOutputStream arrayOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		byte[] bytes = null;
		try {
			arrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(arrayOutputStream);
			objectOutputStream.writeObject(object);
			bytes = arrayOutputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	public static Object deserializeObject(byte[] bytes) {
		ByteArrayInputStream arrayInputStream = null;
		ObjectInputStream objectInputStream = null;
		Object object = null;
		try {
			arrayInputStream = new ByteArrayInputStream(bytes);
			objectInputStream = new ObjectInputStream(arrayInputStream);
			object = objectInputStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return object;
	}

}
