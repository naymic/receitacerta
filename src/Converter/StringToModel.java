package Converter;

import Interfaces.IExtendedConverter;
import Model.Model;
import Reflection.ReflectionDAORelation;

public class StringToModel implements IExtendedConverter{


	public Object convert(Object value, Class<?> c) {
		Model model = (Model) ReflectionDAORelation.instanciateObjectByName(c);
		ReflectionDAORelation rdr = new ReflectionDAORelation(model);
		rdr.setPK(Integer.valueOf((String)value));

		return rdr.getObject();
	}

}
