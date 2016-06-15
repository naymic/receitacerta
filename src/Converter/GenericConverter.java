package Converter;

import Interfaces.IModelConverter;
import Interfaces.ISimpleConverter;
import Model.Model;
import Reflection.GenericReflection;
import Reflection.ReflectionDAORelation;

public class GenericConverter {
	
	public static Object convert(Class<?> outputClass, Object value){
		String simpleClassNameInput = value.getClass().getSimpleName();
		String simpleClassNameOnput = outputClass.getSimpleName();
		
		//No Conversion needed
		if(outputClass.getName() == value.getClass().getName())
			return value;
		
		//Check if its a Model Class Output
		if(outputClass.getSuperclass() == Model.class){
			IModelConverter ic = (IModelConverter)GenericReflection.instanciateObjectByName("Converter."+simpleClassNameInput+"ToModel");
			return ic.convert(value, outputClass);
		}
		
		//Check if its a Model Class Input
		if(value.getClass().getSuperclass() == Model.class){
			ModelToInteger ic = new ModelToInteger();
			return ic.convert(value);
		}

		
		ISimpleConverter ic = (ISimpleConverter)GenericReflection.instanciateObjectByName("Converter."+simpleClassNameInput+"To"+outputClass.getClass().getSimpleName());
		return ic.convert(value);
	}
	
	
	
}
