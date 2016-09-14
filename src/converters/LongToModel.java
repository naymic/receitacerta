package converters;

import interfaces.IExtendedConverter;

public class LongToModel implements IExtendedConverter{

	@Override
	public Object convert(Object value, Class<?> c) throws Exception {
		Integer inteter = new Integer(((Long)value).intValue());
		return GenericConverter.convert(c, inteter);
	}

}
