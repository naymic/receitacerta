package Converter;

import Interfaces.IConverter;

public class StringToInteger implements IConverter {

	@Override
	public Object convert(Object entry) {
		String e = (String)entry;
		return Integer.valueOf(e);
	}

}
