package com.example.intecglass.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.intecglass.util.GenericUtil;

public class KeyValuePair extends Base {//implements Comparable<KeyValuePair>{

	private String key;
	
	private String value;

	public KeyValuePair(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public static List<KeyValuePair> getKeyPairListFromString(String str) {
		final List<KeyValuePair> keyPairValues = new ArrayList<KeyValuePair>();
		final Map<String, String> vals = GenericUtil.getMapFromString(str);
		
		for(String key : vals.keySet()) {
			keyPairValues.add(new KeyValuePair(key, vals.get(key)));
		}
		
		return keyPairValues;
	}

	@Override
	public int compareTo(Base entity) {
		if (!(entity instanceof KeyValuePair)) {
			return -1;
		}
		final KeyValuePair other = (KeyValuePair) entity;
		
		return value.compareTo(other.value);
	}
}
