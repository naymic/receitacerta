package discovery;

import java.lang.reflect.Method;

import annotations.Entity;
import enums.MType;
import model.Describe;
import model.Model;
import reflection.ReflectionDAO;

public class DescribeAttribute extends Describe{
	String entity;

	public DescribeAttribute(ReflectionDAO rd, Method method){
		this.dsetName(rd.getAttributeNameFromMethod(method));
		
		Entity e = rd.getEntity(method);
		this.setEntity(e.toString());
	}
	
	
	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	

	
}
