package converters;

import interfaces.IExtendedConverter;
import interfaces.ISimpleConverter;
import reflection.GenericReflection;

public class NullToInteger implements IExtendedConverter{

	@Override
	public Object convert(Object value, Class<?> c) throws Exception {
		return new Integer(-1);
	}

	

}
