package controllers;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;

import model.Model;

public class ObjectLockerCache implements interfaces.ICache{
	
	private final static String cacheVar = "cache";
	ServletContext sc;
	//HashMap<Class<?>, ArrayList<Model>> cache;
	private static ObjectLockerCache c = null;
	
	
	public static ObjectLockerCache getInstance(){
		if(c == null){
			c = new ObjectLockerCache();
		}
		return c;
	}
	
	private ObjectLockerCache(){
		HashMap<Class<?>, ArrayList<Model>> cache = new HashMap<Class<?>, ArrayList<Model>>();
		sc.setAttribute(cacheVar, cache);
	}
	
	
	public void addToCache(Class<?> objectClass, Integer pk, Model value){
		if(!this.existClassInCache(objectClass)){
			this.getServletContext().put(objectClass, new ArrayList<Model>());
		}
		((HashMap<Class<?>, ArrayList<Model>>)sc.getAttribute(cacheVar)).get(objectClass).add(pk, value);
		
	}
	
	public Model getObjectFromCache(Class<?> objectClass, Integer pk){
		Model mod = (Model) this.existObjectInCache(objectClass, pk);
		
		if(mod != null)
			return mod;
		else
			return null;
	}
	
	public boolean existClassInCache(Class<?> objectClass){
		return this.getServletContext().containsKey(objectClass);
	}
	
	public Model existObjectInCache(Class<?> objectClass, Integer pk){
		try{
			return this.getServletContext().get(objectClass).get(pk);
		}catch(Exception e){
			return null;
		}
	}
	
	private HashMap<Class<?>, ArrayList<Model>> getServletContext(){
		return (HashMap<Class<?>, ArrayList<Model>>) sc.getAttribute(cacheVar);
	}

	@Override
	public void removeFromCache(Class<?> objectClass, Integer pk) {
		this.getServletContext().get(objectClass).remove(pk);		
	}
	
	
	
	
	
}
