package converters;

import interfaces.IExtendedConverter;
import model.Model;
import reflection.GenericReflection;

public class NullToModel implements IExtendedConverter{

	@Override
	public Object convert(Object value, Class<?> c) throws Exception {
		return (Model)GenericReflection.instanciateObjectByName(c);
	}

}
