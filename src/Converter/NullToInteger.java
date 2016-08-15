package Converter;

import Interfaces.IExtendedConverter;
import Interfaces.ISimpleConverter;
import Reflection.GenericReflection;

public class NullToInteger implements IExtendedConverter{

	@Override
	public Object convert(Object value, Class<?> c) throws Exception {
		return new Integer(-1);
	}

	

}
