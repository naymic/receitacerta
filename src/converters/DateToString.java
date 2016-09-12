package converters;

import java.sql.Date;

import interfaces.ISimpleConverter;

public class DateToString implements ISimpleConverter {

	@SuppressWarnings("deprecation")
	@Override
	public Object convert(Object entry) throws Exception {
		Date d = (Date)entry;
		return d.getDay()+"/"+d.getMonth()+"/"+d.getYear();
	}

}
