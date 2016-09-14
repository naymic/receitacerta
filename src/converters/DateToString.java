package converters;

import java.sql.Date;

import interfaces.ISimpleConverter;

public class DateToString implements ISimpleConverter {

	@SuppressWarnings("deprecation")
	@Override
	public Object convert(Object entry) throws Exception {
		Date date = (Date)entry;
		Integer iy = new Integer(date.getYear());
		Integer im = new Integer(date.getMonth());
		Integer id = new Integer(date.getDate());
		
		
		String d = getStringNumber(id);
		String m = getStringNumber(im);
		String y = iy.toString();
		
		return d+"/"+m+"/"+y;
	}
	
	
	private String getStringNumber(Integer value){
		if(value < 10)
			return "0"+value.toString();
		else
			return value.toString();
	}

}
