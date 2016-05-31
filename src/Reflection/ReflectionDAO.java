package Reflection;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import Model.Model;


/**
 * 
 * @author naymic
 * This class is used special used in the DAO (Data Access Object) class
 * It can find out all necessary informations about a object of a table 
 * in the Database
 */
public class ReflectionDAO extends GenericReflection{

	ArrayList<Method> getMethods;
	ArrayList<Method> getMethodsPK;
	ArrayList<Method> getMethodsFK;
	ArrayList<Method> setMethods;
	ArrayList<Method> setMethodsPK;
	ArrayList<Method> setMethodsFK;
	
	/**
	 * Instanciate the object an executes the inicial methods
	 * @param object
	 */
	public ReflectionDAO(Model object){
		super(object);
		initGetMethods();
		initSetMethods();
	}
	
	/**
	 * Prepare a list for all table gettes ("dget") methods,
	 * PK methods, and FK methods
	 * 
	 */
	private void initGetMethods(){
		getMethods = this.getMethodsByName("dget", false, false);
		getMethodsPK = this.getMethodsByName("dget", true, false);
		getMethodsFK = this.getMethodsByName("dget", false, true);
	}
	
	private void initSetMethods(){
		setMethods = this.getMethodsByName("dset", false, false);
		setMethodsPK = this.getMethodsByName("dset", true, false);;
		setMethodsFK = this.getMethodsByName("dset", false, true);
	}
	
	private ArrayList<Method> getMethodsByName(String partOfMethodName, boolean isPK, boolean isFK){
		ArrayList<Method> list = new ArrayList<>();
		
		Method[] ms = this.getObject().getClass().getMethods();
		
		for(Method m : ms){
			if(m.getName().startsWith(partOfMethodName) && this.getEntity(m) != null){
				
				if( (isPK && this.isPK(m)) || (isFK && this.isFK(m)) || (!isPK && !isFK) ){
					list.add(m);
				}
			}
		}
		return list;	
	}
	
	/**
	 * Get a Hashtable with all fields
	 * @return Hashtable<String, Object>
	 */
	public Hashtable<String, Object> getFields(){
		Hashtable<String, Object> fields = new Hashtable<String, Object>();
		Iterator<Method> ms = this.getMethods().iterator();
		
		while(ms.hasNext()){
			Method m = ms.next();
			fields.put(m.getName(), this.getMethodValue(m.getName()));
		}
		
		return fields;
	}
	
	/**
	 * Get a String vector of all table colums
	 * @return String[]
	 */
	public String[] getColums(ArrayList<Method> methods){
		String[] colums = new String[methods.size()];
		Iterator<Method> it= methods.iterator();
		Method m = null;
		Entity e = null;
		
		for(int i=0; it.hasNext(); i++){
			m = it.next();
			e = this.getEntity(m);
			colums[i] = e.attributeName();
		}
		return colums;
	}
	
	/**
	 * Get a Hashtable of each row with value object and VarType enum
	 * @return Hashtable<Object, Vartype>
	 */
	public Object[] getValues(ArrayList<Method> methods){
		Object[] valueList = new Object[methods.size()];

		for(int i=0; i<methods.size(); i++){
			valueList[i] = this.getMethodValue(methods.get(i));
		}
		return valueList;
	}
	
	/**
	 * Get a String vector for a prepared sql statement '?'
	 * @return String[]
	 */
	public String[] getPreparedValues(ArrayList<Method> methods){		
		String[] prepValues = new String[methods.size()];
		
		for(int i=0; i<this.getMethods.size(); i++){
			prepValues[i] = "?";
		}
		
		return prepValues;
	}
		
	
	/**
	 * Get the entity instance of a method 
	 * -> if method is a setter it is changed to a getter 
	 *    and returns annotation of the getter
	 * @param method
	 * @return
	 */
	private Entity getEntity(Method method) {
		
		method = getGetMethod(method);
		
		Entity e =  method.getAnnotation(Entity.class);
		
		if(e == null)
			throw new RuntimeException("Method:" + method.getName() + " in "+ this.getObject().getClass() +" has no Entity");
		return 	e;			
	}
	
	
	/**
	 * Return the class of the entity that a set or get method represents
	 * @param m
	 * @return
	 */
	public Class<?> getMethodValueClass(Method m){
		return this.getGetMethod(m).getReturnType();
	}
	
	/**
	 * Returns always the getMethod 
	 * if param method is a setMethod is is changed inside
	 * @param method
	 * @return
	 */
	public Method getGetMethod(Method method){
		if(method.getName().contains("set")){
			String methodName = method.getName().replace("set", "get");
			method = this.getMethod(methodName);
		}
		return method;
	}
	
	
	
	/**
	 * Get the column name of a Method
	 * @param method
	 * @return
	 */
	private String getColumnName(Method method){

		return getEntity(method).attributeName();
	}
	
	/**
	 * Get a column value
	 * @param method
	 * @return
	 */
	private String getColumnValue(Method method){
		return this.getMethodValue(method.getName()).toString();
	}
	
	/**
	 * Get a column value
	 * @param method
	 * @return
	 */
	private void setColumnValue(String methodName, Object value){		
		this.setMethodValue(methodName, value);
	}
	

	
	private void setColumnValue(Method m, Object value){
		this.setColumnValue(m.getName(), value);
	}
	
	
	private Method getMethodByColumname(String columname){		
		for(Method m : setMethods){
			if(this.getEntity(m).attributeName().equals(columname)){
				return m;
			}
		}
		
		
		try{
			throw new Exception("Exist no method for the columname "+ columname + " in the "+ this.getObject().getClass());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
		
	/**
	 * Check if the method returns a primary key
	 * @param method
	 * @return
	 */
	private boolean isPK(Method method){
		Entity e = this.getEntity(method);
		return e.pk();
	}
	
	/**
	 * Check if the method returns a foreign key
	 * @param method
	 * @return
	 */
	private boolean isFK(Method method){
		Entity e = this.getEntity(method);
		return e.fk();
	}

	
	/*	Common Setters & Getters	 */
	
	/**
	 * Get all primary keys of a table
	 * @return
	 */
	public ArrayList<Method> getGetPKs(){		
		return getMethodsPK;
	}
	
	/**
	 * Get all foreign keys of a table
	 * @return
	 */
	public ArrayList<Method> getGetFKs(){		
		return getMethodsFK;		
	}
	
	/**
	 * Get all methods of an object
	 * @return
	 */
	public ArrayList<Method> getMethods(){
		return this.getMethods;
	}
	
	/**
	 * Get all primary keys of a table
	 * @return
	 */
	public ArrayList<Method> getSetPKs(){		
		return setMethodsPK;
	}
	
	/**
	 * Get all foreign keys of a table
	 * @return
	 */
	public ArrayList<Method> getSetFKs(){		
		return setMethodsFK;		
	}
	
	/**
	 * Get all set methods of an object
	 * @return
	 */
	public ArrayList<Method> getGetMethods(){
		return this.getMethods;
	}
	
	/**
	 * Get all set methods of an object
	 * @return
	 */
	public ArrayList<Method> getSetMethods(){
		return this.setMethods;
	}
	
}
