package jsonclasses;

import java.util.HashMap;
import java.util.Iterator;

public class JObject {
	String className;
	HashMap<String, Object> attributes;
	
	public JObject(){
		attributes = new HashMap<>();
	}
	
	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}
	
	public void addAttribute(String attributeName, Object value){
		attributes.put(attributeName, value);
	}
	
	public boolean existAttribute(String attributeName){
		return attributes.containsKey(attributeName);
	}
	
	public boolean removeAttribute(String attributeName){
		if(existAttribute(attributeName)){
			attributes.remove(attributeName);
			return true;
		}else{
			return false;
		}
	}
	
	public Object getAttribute(String attributeName){
		if(existAttribute(attributeName)){
			return attributes.get(attributeName);
		}else{
			return null;
		}
	}
	
	public Iterator<String> getAttributeKeys(){
		return attributes.keySet().iterator();
	}

	public Iterator<Object> getAttributeValues() {
		// TODO Auto-generated method stub
		return attributes.values().iterator();
	}
}


