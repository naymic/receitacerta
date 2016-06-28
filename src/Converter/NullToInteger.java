package Converter;

import Interfaces.ISimpleConverter;

public class NullToInteger implements ISimpleConverter {

	@Override
	public Object convert(Object entry) throws Exception {
		// TODO Auto-generated method stub
		return new Integer(0);
	}

}
