package Reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import Model.Model;

/**
 * Class that uses refletion and helps to 
 * manage relation between Objects 1 to N
 * @author naymic
 *
 */
public class ReflectionDAORelation extends ReflectionDAO {

	
	public ReflectionDAORelation(Model object) {
		super(object);
	}

	/*
	public HashMap<String, Object> getPKRelationMethods(Model object){
		ReflectionDAO rd = new ReflectionDAO(object);
		HashMap<String, Object> hm = new HashMap<>();
		ArrayList<Method> mlist = rd.getGetPKs();
		
		for(int i=0; i< mlist.size(); i++){
			hm.put(rd.getColumnName(mlist.get(i)), rd.getColumnValue(mlist.get(i)));
		}
		
		return hm;
	}*/
	
	/**
	 * Sets the PK of the instance
	 * @param value 	Object 		Value in object form to set as PK
	 * return 			boolean		[true = value is set | false = exist a exepction]	
	 */
	public boolean setPK(Object value){
		if(!this.checkPK()){
			return false;
		}
		
			
		this.setColumnValue(this.setMethodsPK.get(0), value);
		return true;
		
	}
	
	/**
	 * Gets the PK of the instance
	 * @return Object
	 */
	public Object getPK(){
		
		if(!this.checkPK()){
			return null;
		}
		
		return this.getColumnValue(this.getMethodsPK.get(0));
		
	}
		
	/**
	 * Checks if in the instance exist just one PK and
	 * Checks if PK is not FK too
	 * Throws exception if exist more than one PK or if PK is also an FK
	 */
	private boolean checkPK(){
		try{
			if(this.getSetPKs().size() > 1){
				throw new Exception("The object "+ this.getClass() +" has mor than one PK");
			}
				
			if(this.isFK(this.getMethodsPK.get(0))){
				throw new Exception("The method "+ this.getMethodsPK.get(0).getName() +" from the object "+ this.getClass() +" has mor than one PK");
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
		
		
	/**
	 * Sets a value of an object by its attributeName
	 * @param Object
	 * @param attributeName
	 * @param value
	 */
	public void setValueFromAttributename(String attributeName, Object value){
		
		for(Method m : this.getGetMethods()){
			if(getColumnName(m) == attributeName){
				setMethodRelationValue(getSetMethod(m), value);
			}
		}
		
	}
	
	/**
	 * Returns the PK value of a Relation 1..N in a Model class
	 * @param m
	 * @return int	PK value of the relation class
	 */
	public Object getMethodRelationValue(Method m){

		if(super.getMethodValue(m.getName()) != null && super.getMethodValue(m.getName()).getClass().getName().contains("Model")){
			Model mod = (Model)super.getMethodValue(m.getName());
			ReflectionDAORelation rdr = new ReflectionDAORelation(mod);
			
			return rdr.getPK();
		}
		
		return super.getMethodValue(m.getName());
	}
	
	
	/**
	 * Sets the PK value of a Relation 1..N in a Model class
	 * @param m
	 * @param value
	 */
	public void setMethodRelationValue(Method m, Object value){

		if(super.getMethodValueClass(m).getName().contains("Model.")){
			Model obj = ReflectionDAORelation.instanciateObjectByName(super.getMethodValueClass(m).getName());
			ReflectionDAORelation rdr = new ReflectionDAORelation(obj);
			rdr.setPK(value);
			value = obj;
		}
		
		super.setMethodValue(m.getName(), value);
	}
	
	/**
	 * Sets the PK value of a Relation 1..N in a Model class
	 * @param methodName
	 * @param value
	 */
	public void setMethodRelationValue(String methodName, Object value){
		Method m = this.getMethod(methodName, this.getMethodValueClass(methodName));
		
		this.setMethodRelationValue(m, value);
	}
	
}