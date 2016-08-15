package Converter;

import java.util.Date;

import Interfaces.IExtendedConverter;

public class NullToDate implements IExtendedConverter{

	@Override
	public Object convert(Object value, Class<?> c) throws Exception {
		// TODO Auto-generated method stub
		return new Date(null);
	}

}
