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
		if(value == null || value.toString().length() == 0){
			IExtendedConverter ic;
			if(GenericConverter.checkAssignableModel(outputClass)){
				ic = (IExtendedConverter)GenericReflection.instanciateObjectByName("converters.NullToModel");
			}else{
				ic = (IExtendedConverter)GenericReflection.instanciateObjectByName("converters.NullTo"+outputClass.getSimpleName());
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
		if(GenericConverter.checkAssignableModel(outputClass)){
			IExtendedConverter ic = (IExtendedConverter)GenericReflection.instanciateObjectByName("converters."+simpleClassNameInput+"ToModel");
			return ic.convert(value, outputClass);
		}

		//Check if its a Model Class Input
		if( value != null && GenericConverter.checkAssignableModel(value.getClass())){
			ModelToInteger ic = new ModelToInteger();
			return ic.convert(value);
		}
		
		//Instantiate the converter and converts the value with the interface
		Object obj = null;
		ISimpleConverter ic = (ISimpleConverter)GenericReflection.instanciateObjectByName("converters."+simpleClassNameInput+"To"+simpleClassNameOnput);
		
		obj = ic.convert(value);
		return obj;
	}
	
	/**
	 * Check if an object is a subclass of Model
	 * @param objectClass
	 * @return
	 */
	public static boolean checkAssignableModel(Class<?> objectClass){		
		return Model.class.isAssignableFrom(objectClass);
		
	}

}
