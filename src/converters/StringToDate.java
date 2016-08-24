package converters;

import java.sql.Date;

import interfaces.ISimpleConverter;

public class StringToDate implements ISimpleConverter {

	@Override
	public Object convert(Object entry) {
		return Date.valueOf((String) entry);
	}

}
