package discovery;

import java.lang.reflect.Method;

import annotations.Entity;
import enums.MType;
import model.Model;
import reflection.ReflectionDAO;

public class DescribeAttribute {
	Entity entity;
	String attributeName;

	public DescribeAttribute(ReflectionDAO rd, Method method){
		this.setAttributeName(attributeName);
		
		Entity e = rd.getEntity(method);
		this.setEntity(e);
	}
	
	
	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	
}
