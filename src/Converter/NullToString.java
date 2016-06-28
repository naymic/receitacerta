package Converter;

import Interfaces.ISimpleConverter;

public class NullToString implements ISimpleConverter {

	@Override
	public Object convert(Object entry) throws Exception {
		// TODO Auto-generated method stub
		return new String("");
	}

}
