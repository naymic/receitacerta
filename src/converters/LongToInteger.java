package converters;

import java.math.BigInteger;

import interfaces.ISimpleConverter;

public class LongToInteger implements ISimpleConverter{

	@Override
	public Object convert(Object entry) throws Exception {
		Long bigint = (Long)entry;
		Integer i = new Integer(bigint.intValue());
		return i;
	}

}
