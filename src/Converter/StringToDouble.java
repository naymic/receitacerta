package Converter;

import Interfaces.ISimpleConverter;

public class StringToDouble implements ISimpleConverter {

	@Override
	public Object convert(Object entry) {
		return Double.valueOf((String) entry);
	}

}
