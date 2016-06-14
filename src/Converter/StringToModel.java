package Converter;

import GenericDao.DAO;
import Interfaces.IConverter;
import Model.Model;
import Reflection.ReflectionDAORelation;

public class StringToModel {


	public Object convert(Object value, Class<?> c) {
		Model model = (Model) ReflectionDAORelation.instanciateObjectByName(c);
		ReflectionDAORelation rdr = new ReflectionDAORelation(model);
		rdr.setPK(value);

		return rdr.getObject();
	}

}
