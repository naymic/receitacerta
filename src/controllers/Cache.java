package controllers;

import java.util.ArrayList;
import java.util.HashMap;

import model.Model;

public class Cache {
	
	HashMap<Class<?>, ArrayList<Model>> cache;
	private static Cache c = null;
	
	
	public static Cache getInstance(){
		if(c == null){
			c = new Cache();
		}
		return c;
	}
	
	private Cache(){
		cache = new HashMap<Class<?>, ArrayList<Model>>();
	}
	
	
	public void addToCache(Class<?> objectClass, Integer pk, Model value){
		/*if(!this.existClassInCache(objectClass)){
			cache.put(objectClass, new ArrayList<Model>());
		}*/
		cache.get(objectClass).add(pk, value);
		
	}
	
	public Model getObjectFromCache(Class<?> objectClass, Integer pk){
		if(this.existObjectInCache(objectClass, pk))
			return cache.get(objectClass).get(pk);
		else
			return null;
	}
	
	public boolean existClassInCache(Class<?> objectClass){
		return cache.containsKey(objectClass);
	}
	
	public boolean existObjectInCache(Class<?> objectClass, Integer pk){
		try{
			cache.get(objectClass).get(pk);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	
	
	
	
}
