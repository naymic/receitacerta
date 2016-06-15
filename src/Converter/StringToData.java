package Converter;

import java.sql.Date;

import Interfaces.ISimpleConverter;
import Model.Model;

public class StringToData implements ISimpleConverter {

	@Override
	public Object convert(Object entry) {
		return Date.valueOf((String) entry);
	}

}
