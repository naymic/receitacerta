package Converter;

import Interfaces.IConverter;
import Reflection.GenericReflection;

public class GenericConverter {
	
	public static Object convert(String simpleClassNameOutput, Object value){
		String simpleClassNameInput = value.getClass().getSimpleName();
		
		IConverter ic = (IConverter)GenericReflection.instanciateObjectByName("Converter."+simpleClassNameInput+"To"+simpleClassNameOutput);
		return ic.convert(value);
	}
	
	
	
}
