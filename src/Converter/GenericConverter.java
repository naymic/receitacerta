package Converter;

import Interfaces.IModelConverter;
import Interfaces.ISimpleConverter;
import Model.Model;
import Reflection.GenericReflection;

public class GenericConverter {

	public static Object convert(Class<?> outputClass, Object value) throws Exception{
		String simpleClassNameInput;
		
		if(value == null){
			simpleClassNameInput = "Null";
		}else{
			simpleClassNameInput = value.getClass().getSimpleName();
		}
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
		if( value != null && value.getClass().getSuperclass() == Model.class){
			ModelToInteger ic = new ModelToInteger();
			return ic.convert(value);
		}

		Object obj = null;
		ISimpleConverter ic = (ISimpleConverter)GenericReflection.instanciateObjectByName("Converter."+simpleClassNameInput+"To"+simpleClassNameOnput);
		
		obj = ic.convert(value);
		return obj;
	}

}
