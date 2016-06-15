package Converter;

import Model.Model;
import Reflection.ReflectionDAORelation;

public class ModelToInteger{
	
	public Object convert(Object value){
		ReflectionDAORelation rdr = new ReflectionDAORelation((Model)value);
		return rdr.getPK();
	}
	
}
