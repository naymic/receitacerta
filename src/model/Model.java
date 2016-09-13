package model;

import java.lang.reflect.Method;
import java.util.ArrayList;

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
			if(rd.isRequired(m) && !rd.isPK(m) && (rd.getMethodValue(m) == null || rd.getMethodValue(m).toString().length() == 0)){
				r.addAttributeError(rd.getObject().getClass().getSimpleName(),rd.getColumnName(m),"Attribute: "+ rd.getColumnName(m) +" is required but null in object ");
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
	
	
	
}
