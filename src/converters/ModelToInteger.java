package converters;

import model.Model;
import reflection.ReflectionDAORelation;

public class ModelToInteger{
	
	public Object convert(Object value){
		ReflectionDAORelation rdr = new ReflectionDAORelation((Model)value);
		return rdr.getPK();
	}
	
}
