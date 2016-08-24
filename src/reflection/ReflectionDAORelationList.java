package reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;

import model.Model;

public class ReflectionDAORelationList extends ReflectionDAORelation{
	private ArrayList<Method> getMethodArray;
	private ArrayList<Method> setMethodArray;
	
	
	public ReflectionDAORelationList(Model object){
		super(object);
		
		this.getMethodArray = this.getObjectMethods("aGet", false , true);
		this.setMethodArray = this.getObjectMethods("aSet", false, true);
		
	}
	
	/**
	 * Get the array of get methods of arrays
	 * @return ArrayList<Method>
	 */
	public ArrayList<Method> getGetArray(){
		return this.getMethodArray;
	}

	/**
	 *
	 * Get the array of set methods of arrays
	 * @return ArrayList<Method>
	 */
	public ArrayList<Method> getSetArray(){
		return this.setMethodArray;
	}
	
	/**
	 * Set the mapped object in a sub object
	 * mappedObject 1..N subObject (the mappedObject exist N times in the list of subObjects)
	 * @param mappedObject
	 * @param object
	 */
	public static void setMappedObject(Model mappedObject, Model object){
		ReflectionDAORelation rdr = new ReflectionDAORelation(object);
		ArrayList<Method> methodList = rdr.getMethods();
		for(Method m : methodList){
			if(rdr.getMethodValueClass(m) == mappedObject.getClass()){
				rdr.setMethodValue(m, mappedObject);
			}
		}
		
	}
	
}
