package Converter;

import java.sql.Date;

import Interfaces.IConverter;
import Model.Model;

public class StringToData implements IConverter {

	@Override
	public Object convert(Object entry) {
		return Date.valueOf((String) entry);
	}

}
