package jresponseclasses;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import annotations.Entity;
import enums.FieldType;
import model.Model;

public class JDataType {
	String fieldClassification;
	String objectClass;
	boolean required;
	
		
	/**
	 * 
	 * @param objectType
	 * @param fieldClassification
	 */
	JDataType(Field field, Entity e){
		this.prepareAndSetObject(field, e);		
	}
	
	private void prepareAndSetObject(Field obj, Entity e){
		Class<?> objClass = obj.getType();
		
		//Set the classname of the given field
		this.setObjectClassName(objClass.getSimpleName());
		
		if(e != null){
			this.setRequired(e.required());
		}
		
		//Set the Fieldclassification
		if(Model.class.isAssignableFrom(objClass))
			this.setFieldClassification(FieldType.MODEL);
		else
			this.setFieldClassification(FieldType.PRIMITIVE);
			
		
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

	public boolean isRequired() {
		return required;
	}


	public void setRequired(boolean required) {
		this.required = required;
	}
	
}
