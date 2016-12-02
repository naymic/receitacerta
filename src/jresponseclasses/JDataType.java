package jresponseclasses;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import annotations.AEntity;
import enums.EFieldType;
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
	JDataType(Field field, AEntity e){
		this.prepareAndSetObject(field, e);		
	}
	
	private void prepareAndSetObject(Field obj, AEntity e){
		Class<?> objClass = obj.getType();
		
		//Set the classname of the given field
		this.setObjectClassName(objClass.getSimpleName());
		
		if(e != null){
			this.setRequired(e.required());
		}
		
		//Set the Fieldclassification
		if(Model.class.isAssignableFrom(objClass))
			this.setFieldClassification(EFieldType.MODEL);
		else
			this.setFieldClassification(EFieldType.PRIMITIVE);
			
		
	}
	
	public String getFieldClassification() {
		return fieldClassification;
	}

	public void setFieldClassification(EFieldType fieldClassification) {
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
