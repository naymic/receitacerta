package converters;

import java.lang.reflect.Method;
import java.util.ArrayList;

import annotations.AModelClasses;
import dao.DAO;
import interfaces.IExtendedConverter;
import model.Model;
import reflection.ReflectionDAORelation;
import reflection.ReflectionModel;

public class IntegerToModel implements IExtendedConverter{
	
	public Object convert(Object value, Class<?> c) throws Exception{
		Model model = (Model) ReflectionDAORelation.instanciateObjectByName(c);
		ReflectionDAORelation rdr = new ReflectionDAORelation(model);
		rdr.setPK(value);
		
		 //Select it from the database
		rdr.setObject(DAO.getInstance().select(model).get(0));
		if(rdr.getObject() == null){
			new Exception("Object "+rdr.getObject().getClass().getName()+ " with PK "+ value +" not found!");
		}
		
		
		return rdr.getObject();
	}
	
	
	
}
