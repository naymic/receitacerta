package model;

import java.lang.reflect.Method;
import java.util.ArrayList;

import annotations.AModelClasses;
import enums.ObjectState;
import jsonclasses.JReturn;
import reflection.ReflectionDAO;

public abstract class Model {
	
	ObjectState objState;
	
	public abstract String getTableName();
	
	//public abstract Return verify(Return r, boolean checkSuper);
	
	public void verifyGeneric(JReturn r){
		ReflectionDAO rd = new ReflectionDAO(this);
		
		ArrayList<Method> getMethods = rd.getGetMethods();
		for (int i = 0; i < getMethods.size(); i++) {
			Method m = getMethods.get(i);
			Object methodValue = rd.getMethodValue(m);
			try{
				
				if(rd.isRequired(m) && !rd.isPK(m) && (methodValue == null || methodValue.toString().length() == 0) && this.isUserObjectRequired(rd.getObjectClass(), rd.getMethodValueClass(m))){
					r.addAttributeError(rd.getObject().getClass().getSimpleName(),rd.getColumnName(m),"Attribute: "+ rd.getColumnName(m) +" is required but null in object ");
				}
			}catch(NullPointerException npe){
				r.addSimpleError("Annotation AModelClasses not set in Class: "+ rd.getMethodValueClass(m).getName());
			}
			
			
			
		}
		
		
		verify(r);
		
	}
	
	public abstract void verify(JReturn r);
	

	/**
	 * Get the storage state of this object
	 * @return
	 */
	public ObjectState getObjState() {
		return objState;
	}

	/**
	 * Set the storage state of this object
	 * @param objState
	 */
	public void setObjState(ObjectState objState) {
		this.objState = objState;
	}
	

	private boolean isUserObjectRequired(Class<?> objectClass, Class<?> attributeClass)throws NullPointerException {
			
			if(!Model.class.isAssignableFrom(attributeClass))
				return true;
		
			//if the attribute ist not user model, always true
			AModelClasses amcValue = attributeClass.getAnnotation(AModelClasses.class); 
			if(!amcValue.isUserModel())
				return true;
			
			//if the user object is required return false, if its not so it need to be checked
			AModelClasses amc = objectClass.getAnnotation(AModelClasses.class); 
			return !amc.needUserObject();
	}

	
}
