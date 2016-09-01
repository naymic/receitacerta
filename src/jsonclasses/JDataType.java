package jsonclasses;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import enums.FieldType;
import model.Model;

public class JDataType {
	String fieldClassification;
	String objectClass;
	
		
	/**
	 * 
	 * @param objectType
	 * @param fieldClassification
	 */
	JDataType(Field field){
		this.prepareAndSetObject(field);		
	}
	
	
	public String getFieldClassification() {
		return fieldClassification;
	}

	public void setFieldClassification(FieldType fieldClassification) {
		this.fieldClassification = fieldClassification.name();
	}

	public String getObjectClassName() {
		return objectClass;
	}

	public void setObjectClassName(String objectTypeName) {
		this.objectClass = objectTypeName;
	}

	private void prepareAndSetObject(Field obj){
		Class<?> objClass = obj.getType();
		
		//Set the classname of the given field
		this.setObjectClassName(objClass.getSimpleName());
		
		
		//Set the Fieldclassification
		if(Model.class.isAssignableFrom(objClass))
			this.setFieldClassification(FieldType.MODEL);
		else
			this.setFieldClassification(FieldType.PRIMITIVE);
			
		
	}
	
}
