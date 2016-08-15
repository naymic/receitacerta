package Converter;

import java.sql.Date;

import Interfaces.ISimpleConverter;

public class StringToDate implements ISimpleConverter {

	@Override
	public Object convert(Object entry) {
		return Date.valueOf((String) entry);
	}

}
