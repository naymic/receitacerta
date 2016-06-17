package Converter;

import GenericDao.DAO;
import Interfaces.IModelConverter;
import Model.Model;
import Reflection.ReflectionDAORelation;

public class IntegerToModel implements IModelConverter{
	
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
