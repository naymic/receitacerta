package converters;



public class NullToString implements interfaces.IExtendedConverter{

	@Override
	public Object convert(Object value, Class<?> c) throws Exception {
		return new String("");
	}
	
}
