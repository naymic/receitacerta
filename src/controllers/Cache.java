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
		Model mod = (Model) this.existObjectInCache(objectClass, pk);
		
		if(mod != null)
			return mod;
		else
			return null;
	}
	
	public boolean existClassInCache(Class<?> objectClass){
		return cache.containsKey(objectClass);
	}
	
	public Model existObjectInCache(Class<?> objectClass, Integer pk){
		try{
			return cache.get(objectClass).get(pk);
		}catch(Exception e){
			return null;
		}
	}
	
	
	
	
	
}
