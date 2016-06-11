package Converter;

import Interfaces.IConverter;

public class StringToDouble implements IConverter {

	@Override
	public Object convert(Object entry) {
		return Double.valueOf((String) entry);
	}

}
