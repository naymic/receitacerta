package Reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Converter.GenericConverter;
import GenericDao.DAO;
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
	 * Gets the PK of the instance
	 * @return Object
	 */
	public Object getSelectValue(){
		
		for(Method m : this.getMethods()){
			if(!this.isPK(m) && this.isRequired(m))
				return this.getColumnValue(m);
		}
		
		return null;
		
		
		
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
		
		//value = (this.g)
		
		for(Method m : this.getGetMethods()){
			if(getColumnName(m).equals(attributeName)){
				setMethodRelationValue(getSetMethod(m), value);
				break;
			}
		}
		
	}
	
	
	/**
	 * Returns the PK value of a Relation 1..N in a Model class
	 * @param columnName String
	 * @return int	PK value of the relation class
	 */
	public Object getMethodRelationId(String colunmName){
		Method m = this.getGetMethodByColumname(colunmName);
		return this.getMethodRelationId(m);
	}
	
	/**
	 * Returns the PK value of a Relation 1..N in a Model class
	 * @param m
	 * @return int	PK value of the relation class
	 */
	public Object getMethodRelationId(Method m){

		if(super.getMethodValue(m.getName()) != null && super.getMethodValue(m.getName()).getClass().getName().contains("Model")){
			Model mod = (Model)super.getMethodValue(m.getName());
			ReflectionDAORelation rdr = new ReflectionDAORelation(mod);
			
			return rdr.getPK();
		}
		
		return super.getMethodValue(m.getName());
	}
	
	
	
	/**
	 * Returns the PK value of a Relation 1..N in a Model class
	 * @param m
	 * @return int	PK value of the relation class
	 */
	public Object getMethodRelationValue(String colunmName){
		Method m = this.getGetMethodByColumname(colunmName);
		return this.getMethodRelationValue(m);
	}
	
	/**
	 * Returns the PK value of a Relation 1..N in a Model class
	 * @param m
	 * @return int	PK value of the relation class
	 */
	public Object getMethodRelationValue(Method m){
		m = this.getGetMethod(m);
		
		if(getMethodValue(m) != null && super.getMethodValue(m.getName()).getClass().getName().contains("Model")){
			Model mod = (Model)super.getMethodValue(m.getName());
			ReflectionDAORelation rdr = new ReflectionDAORelation(mod);
			
			return rdr.getSelectValue();
		}
		
		return super.getMethodValue(m.getName());
	}
	
	/**
	 * Get a Hashtable of each row with value object and VarType enum
	 * @return Hashtable<Object, Vartype>
	 */
	public Object getValueFromAttributeName(String attributeName, boolean checkRelation){

		if(checkRelation){
			for(int i=0; i<this.getMethods().size(); i++){
				Method m = this.getMethods.get(i);
				if(this.getColumnName(m).equalsIgnoreCase(attributeName) && this.getMethodValue(m).getClass().getName().contains("Model.")){					
					ReflectionDAORelation rdr = new ReflectionDAORelation( (Model) this.getMethodValue(m));
					return this.getMethodRelationValue(m);
				}else{
					return this.getMethodValue(m);
				}
			}
		}else{
			return getValueFromAttributeName(attributeName);
		}

		return null;
	}
	
	
	/**
	 * Sets the PK value of a Relation 1..N in a Model class
	 * @param m
	 * @param value
	 */
	public void setMethodRelationValue(Method m, Object value){
		
		try{
		value = GenericConverter.convert(this.getMethodValueClass(m), value);
		}catch(Exception e){
			System.out.println("Error in ReflectionDAORelation in setMethodRelationValue: "+ e.getMessage());
		}
		
		super.setMethodValue(m.getName(), this.getMethodValueClass(m) , value);
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
