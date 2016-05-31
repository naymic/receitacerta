package Reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class GenericReflection {
	
	protected Object object;
	
	public GenericReflection(Object object){
		setObject(object);
	}	
	
	public Method getMethod(String methodName){
		Method m = null;
		
		try {
				 m = this.getObject().getClass().getMethod(methodName, null);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return m;
	}
	
	public Object getMethodValue(String methodName){
		Object value = null;	
				try {
					value = this.getObject().getClass().getMethod(methodName, null).invoke(this.getObject(), null);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return value;
	}
	
	public Object getMethodValue(Method m){
		return this.getMethodValue(m.getName());
	}
	
	
	public boolean setMethodValue(String methodName, Object value){
		try {
			this.setMethodValue(this.getObject().getClass().getMethod(methodName, value.getClass()), value);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean setMethodValue(Method m, Object value){
		try {
			m.invoke(this.getObject(), value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}


	public Object getObject() {
		return object;
	}


	public void setObject(Object object) {
		this.object = object;
	}
	
	public Object cloneObject(Object object){
		try {
			return object.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	
}
