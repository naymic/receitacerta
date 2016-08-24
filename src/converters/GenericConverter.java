package converters;

import interfaces.IExtendedConverter;
import interfaces.ISimpleConverter;
import model.Model;
import reflection.GenericReflection;

public class GenericConverter {

	/**
	 * Convert a value by given output class and the proper input value class
	 * @param outputClass
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static Object convert(Class<?> outputClass, Object value) throws Exception{
		String simpleClassNameInput;
		
		
		//Objec value is null?
		if(value == null){
			IExtendedConverter ic;
			if(outputClass.getSuperclass() == Model.class){
				ic = (IExtendedConverter)GenericReflection.instanciateObjectByName("Converter.NullToModel");
			}else{
				ic = (IExtendedConverter)GenericReflection.instanciateObjectByName("Converter.NullTo"+outputClass.getSimpleName());
			}
			return ic.convert(value, outputClass);
		}else{
			simpleClassNameInput = value.getClass().getSimpleName();
		}
		
		//Set output classname
		String simpleClassNameOnput = outputClass.getSimpleName();

		//No Conversion needed
		if(outputClass.getName() == value.getClass().getName())
			return value;

		//Check if its a Model Class Output
		if(outputClass.getSuperclass() == Model.class){
			IExtendedConverter ic = (IExtendedConverter)GenericReflection.instanciateObjectByName("Converter."+simpleClassNameInput+"ToModel");
			return ic.convert(value, outputClass);
		}

		//Check if its a Model Class Input
		if( value != null && value.getClass().getSuperclass() == Model.class){
			ModelToInteger ic = new ModelToInteger();
			return ic.convert(value);
		}
		
		//Instantiate the converter and converts the value with the interface
		Object obj = null;
		ISimpleConverter ic = (ISimpleConverter)GenericReflection.instanciateObjectByName("Converter."+simpleClassNameInput+"To"+simpleClassNameOnput);
		
		obj = ic.convert(value);
		return obj;
	}

}
