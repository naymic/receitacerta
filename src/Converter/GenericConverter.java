package Converter;

import Interfaces.IConverter;
import Model.Model;
import Reflection.GenericReflection;

public class GenericConverter {
	
	public static Object convert(Class<?> outputClass, Object value){
		String simpleClassNameInput = value.getClass().getSimpleName();
		String simpleClassNameOnput = outputClass.getSimpleName();
		
		//No Conversion needed
		if(outputClass.getName() == value.getClass().getName())
			return value;
		
		//Check if its a Model Class return
		//if(outputClass.getName().contains("Model")){
		if(outputClass.getSuperclass() == Model.class){
			StringToModel ic = new StringToModel();
			return ic.convert(value, outputClass);
		}
		
		IConverter ic = (IConverter)GenericReflection.instanciateObjectByName("Converter."+simpleClassNameInput+"To"+outputClass.getClass().getSimpleName());
		return ic.convert(value);
	}
	
	
	
}
