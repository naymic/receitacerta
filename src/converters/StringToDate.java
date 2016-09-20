package converters;

import java.sql.Date;

import interfaces.ISimpleConverter;

public class StringToDate implements ISimpleConverter {

	@Override
	public Object convert(Object entry) {
		String[] date= ((String)entry).split("-");
		
		@SuppressWarnings("deprecation")
		Date d = new Date(Integer.valueOf(date[0])-1900, Integer.valueOf(date[1]), Integer.valueOf(date[2]));
		return d;
	}

}
