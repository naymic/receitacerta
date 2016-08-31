package converters;

import dao.DAO;
import interfaces.IExtendedConverter;
import model.Model;
import reflection.ReflectionDAORelation;

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
