package Reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import Model.*;

/**
 * Class that has some generic reflection methods
 * @author naymic
 *
 */
public class GenericReflection {
	
	protected Object object;
	
	public GenericReflection(Object object){
		setObject(object);
	}	
	
	
	/**
	 * Finds a method by its name
	 * @param methodName	String	Name of the method to find
	 * @return				Method	Returns found method
	 */
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
	
	/**
	 * Finds a method by its name
	 * @param methodName	String	Name of the method to find
	 * @param cl			Class	Class of set method parameter value, null if is a get method
	 * @return				Method	Returns found method
	 */
	public Method getMethod(String methodName, Class<?> cl){
		Method m = null;
		
		try {
				 m = this.getObject().getClass().getMethod(methodName, cl);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return m;
	}
	
	/**
	 * Gets the value of a get method
	 * @param methodName	String		Name of the get method
	 * @return				Object		Returned value of the given method name	
	 */
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
	
	
	public boolean setMethodValue(String methodName, Class<?> valueClass, Object value){
		try {
			this.setMethodValue(this.getObject().getClass().getMethod(methodName, valueClass), value);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	/**
	 * Sets a value to the object instance by a set method
	 * @param 	m		Method	Set method
	 * @param 	value	Object	Value to set 
	 * @return 			boolean	[true = value is set | false = exception thrown during execution]
	 */
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

	/**
	 * Gets the reflected object
	 * @return
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * Sets the object with objective to reflect it
	 * @param object
	 */
	public void setObject(Object object) {
		this.object = object;
	}
	
	/**
	 * Clones the instance and return a copy of it
	 * @param object
	 * @return	Object	Copy of the actual instance
	 */
	public Object cloneObject(Object object){
		try {
			return object.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * Checks if all primary keys (pk) are set
	 * @return boolean	[false = if one or more of the pk is not set | true = if none of the pk is set]
	 */
	public boolean isValueNull(Method m){
		return this.getMethodValue(m) == null;
	}
	
	
	/**
	 * Instantiate a new Object using a className
	 * @param className
	 * @return
	 */
	public static Object instanciateObjectByName(String className){
		
		Class ref;
		Object obj = null;
		
		try {
			ref = Class.forName(className);
			obj = ref.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
	}

	/**
	 * Instantiate a new Object using a className
	 * @param className
	 * @return
	 */
	public static Object instanciateObjectByName(Class<?> _class){
		return instanciateObjectByName(_class.getName());
	}
	
}
