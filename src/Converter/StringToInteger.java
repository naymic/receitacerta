package Converter;

import Interfaces.ISimpleConverter;

public class StringToInteger implements ISimpleConverter {

	@Override
	public Object convert(Object entry) {
		String e = (String)entry;
		return Integer.valueOf(e);
	}

}
