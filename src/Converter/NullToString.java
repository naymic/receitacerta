package Converter;



public class NullToString implements Interfaces.IExtendedConverter{

	@Override
	public Object convert(Object value, Class<?> c) throws Exception {
		return new String("");
	}
	
}
