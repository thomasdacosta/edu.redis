package edu.redis.devmedia.object;

import java.io.Serializable;

public class SimpleObject implements Serializable {

	private static final long serialVersionUID = 5958861183687046894L;

	private String nameObject;
	private Double numDoubleObject;
	private Integer numIntObject;
	
	public SimpleObject() {
	}

	public String getNameObject() {
		return nameObject;
	}

	public void setNameObject(String nameObject) {
		this.nameObject = nameObject;
	}

	public Double getNumDoubleObject() {
		return numDoubleObject;
	}

	public void setNumDoubleObject(Double numDoubleObject) {
		this.numDoubleObject = numDoubleObject;
	}

	public Integer getNumIntObject() {
		return numIntObject;
	}

	public void setNumIntObject(Integer numIntObject) {
		this.numIntObject = numIntObject;
	}
	
	@Override
	public String toString() {
		return nameObject + "," + numDoubleObject + "," + numIntObject;
	}

}
