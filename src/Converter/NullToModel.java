package Converter;

import Interfaces.IExtendedConverter;
import Model.Model;
import Reflection.GenericReflection;

public class NullToModel implements IExtendedConverter{

	@Override
	public Object convert(Object value, Class<?> c) throws Exception {
		return (Model)GenericReflection.instanciateObjectByName(c);
	}

}
