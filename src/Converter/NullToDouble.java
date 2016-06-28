package Converter;

import Interfaces.ISimpleConverter;

public class NullToDouble implements ISimpleConverter {

	@Override
	public Object convert(Object entry) throws Exception {
		
		return new Double(0.0);
	}

}
