package converters;

import interfaces.ISimpleConverter;

public class IntegerToString implements ISimpleConverter {

	@Override
	public Object convert(Object entry) throws Exception {
		String s = (String)entry;
		return Integer.valueOf(s);
	}

}
