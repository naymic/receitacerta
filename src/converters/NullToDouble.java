package converters;

import interfaces.IExtendedConverter;

public class NullToDouble implements IExtendedConverter{

	@Override
	public Object convert(Object value, Class<?> c) throws Exception {
		// TODO Auto-generated method stub
		return new Double(null);
	}

}
