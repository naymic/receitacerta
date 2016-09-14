package converters;

import java.sql.Date;

import interfaces.IExtendedConverter;

public class NullToDate implements IExtendedConverter{

	@Override
	public Object convert(Object value, Class<?> c) throws Exception {
		// TODO Auto-generated method stub
		return new Date(new Long(null));
	}

}
