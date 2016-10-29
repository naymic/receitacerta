package reflection;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import annotations.AModelClasses;
import annotations.Entity;
import enums.MType;
import model.Model;
import utils.StringUtils;


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
	
	
	public Model getObject() {
		return (Model)super.getObject();
	}
	
	/**
	 * Prepare a list for all table gettes ("dget") methods,
	 * PK methods, and FK methods
	 * 
	 */
	protected void initGetMethods(){
		getMethods = this.getObjectMethods("dget", false, false);
		getMethodsPK = this.getObjectMethods("dget", true, false);
		getMethodsFK = this.getObjectMethods("dget", false, true);
	}
	
	protected void initSetMethods(){
		setMethods = this.getObjectMethods("dset", false, false);
		setMethodsPK = this.getObjectMethods("dset", true, false);;
		setMethodsFK = this.getObjectMethods("dset", false, true);
	}
	
	/**
	 * Returns the Methods by filtering them by the parameters
	 * @param partOfMethodName String
	 * @param isPK boolean
	 * @param isFK boolean
	 * @return ArrayList<Method> Filtered Methods
	 */	
	protected ArrayList<Method> getObjectMethods(String partOfMethodName, boolean isPK, boolean isFK){
		ArrayList<Method> list = new ArrayList<>();
		
		Method[] ms = this.getObjectClass().getMethods();
		
		//Get also methods of inheritance
		/*if(this.getObjectClass().getSuperclass() != Model.class){
			ReflectionDAO superRd = new ReflectionDAO((Model)GenericReflection.instanciateObjectByName(this.getObjectClass().getSuperclass()));
			list = superRd.getObjectMethods(partOfMethodName, isPK, isFK);
		}*/
		
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
	 * Get a Hashtable of each row with value object and VarType enum
	 * @return Hashtable<Object, Vartype>
	 */
	public Object getValueFromAttributeName(String attributeName){
		
		for(int i=0; i<this.getMethods().size(); i++){
			Method m = this.getMethods.get(i);
			if(this.getColumnName(m).equalsIgnoreCase(attributeName))
				return this.getMethodValue(m);
		}
		return null;
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
	public Entity getEntity(Method method) {
		
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
	 * Return the class of the entity that a set or get method represents
	 * @param m
	 * @return
	 */
	public Class<?> getMethodValueClass(String methodName){
		
		if(methodName.contains("dset")){
			methodName = methodName.replace("dset", "dget");
		}
		Method m = this.getMethod(methodName);
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
	 * Returns always the getMethod 
	 * if param method is a setMethod is is changed inside
	 * @param method
	 * @return returns allways the setMethod of an attribute
	 */
	public Method getSetMethod(Method method){
		if(method.getName().contains("get")){
			String methodName = method.getName().replace("get", "set");
			method = this.getMethod(methodName, this.getMethodValueClass(method));
		}
		return method;
	}
	
	
	
	/**
	 * Get the column name of a Method
	 * @param method
	 * @return
	 */
	public String getColumnName(Method method){

		return getEntity(method).attributeName();
	}
	
	/**
	 * Get a column value
	 * @param method
	 * @return
	 */
	protected Object getColumnValue(Method method){
		return this.getMethodValue(method.getName());
	}
	
	/**
	 * Get a column value
	 * @param method
	 * @return
	 */
	protected void setColumnValue(String methodName, Object value){				
		this.setMethodValue(methodName, this.getMethodValueClass(methodName), value);
	}
	

	
	protected void setColumnValue(Method m, Object value){
		this.setColumnValue(m.getName(), value);
	}
	
	
	
	
	public Method getSetMethodByColumname(String columname){				
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
	
	public Method getGetMethodByColumname(String columname){		
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
	
	public Method getMethodByFieldname(String fieldname, MType mt, Class<?>... args){		
		return this.getMethodByFieldname(fieldname, mt,"d", args);
		
	}
	
	public Method getMethodByFieldname(String fieldname, MType mt, String preName, Class<?>... args)throws RuntimeException{		

			try{
			return this.getMethod(preName+mt.name()+StringUtils.setFirstLetterUppercase(fieldname), args);
			}catch(Exception e){
				System.out.println("Exist no method for the columname "+ fieldname + " in the "+ this.getObject().getClass());
				throw new RuntimeException("Exist no method for the columname "+ fieldname + " in the "+ this.getObject().getClass());
			}
		}
		
		
		
	
	/**
	 * Get a method with pre or without prename like "d" or "a"
	 * @param fieldname
	 * @param get
	 * @return
	 */
	public Method getAllMethods(String fieldname,MType mtype, Class<?>... args){
		Method m= null;
		String[] preNames= new String[]{"d","","a"};
		RuntimeException e = null;
		
		for(String preName : preNames){
			try{
				m = this.getMethodByFieldname(fieldname, mtype, preName, args);
				return m;
			}catch(RuntimeException re){
				e = re;
			}
		}
		
		throw e;
		
	}

	/**
	 * Prepares a SQl String ready to execute in a SQL Statement
	 * @param getMethods
	 * @param where
	 * @param search 
	 * @return
	 */
	public String prepareSelectSqlString(ArrayList<Method>  getMethods, ArrayList<Method> where){
		Model object = (Model)this.getObject();
		String[] colums = this.getColums(getMethods);
		String[] _where = this.getColums(where);
		String __where = "";
		if(where.size() > 0){
			__where = "WHERE " + StringUtils.getPrepStmtColumns(_where, " AND");
		}
		
		return "SELECT "+ StringUtils.getCommaString(colums) +" FROM "+ object.getTableName() +" "+ __where;		
	}
	

	
	/**
	 * Prepares a SQl String ready to execute in a SQL Statement
	 * @param getMethods
	 * @param where
	 * @param search 
	 * @return
	 */
	public String prepareSarchSqlString(ArrayList<Method>  getMethods, ArrayList<Method> where){
		Model object = (Model)this.getObject();
		String[] colums = this.getColums(getMethods);
		String[] _where = this.getColums(where);
		String __where = "";
		if(where.size() > 0){
			__where = "WHERE " + StringUtils.getPrepStmtColumnsSearch(this.getValueClassesFromMethods(where) ,_where);
		}
		
		return "SELECT "+ StringUtils.getCommaString(colums) +" FROM "+ object.getTableName() +" "+ __where;		
	}
	
	
	public static Model instanciateObjectByName(String className) throws RuntimeException{
		return (Model)GenericReflection.instanciateObjectByName(className);
	}
	
		
	/*	Methods to hide the Entity class */
	
	/**
	 * Check if the method returns a primary key
	 * @param method
	 * @return
	 */
	public boolean isPK(Method method){
		return this.getEntity(method).pk();
	}
	
	/**
	 * Check if the method returns a foreign key
	 * @param method
	 * @return
	 */
	public boolean isFK(Method method){
		return this.getEntity(method).fk();

	}
	
	/**
	 * Check if the attribute of belonging to that method is required
	 * @param method
	 * @return
	 */
	public boolean isRequired(Method method){
		return this.getEntity(method).required();
	}
	
	/**
	 * Checks if a attribute is mapped
	 * @param obj
	 * @return
	 */
	public boolean isMapped(Method method){
		return this.getEntity(method).isMapped();		
	}
	
	/**
	 * Checks if a object is child of Model
	 * @param obj
	 * @return
	 */
	public static boolean isModelClass(Object obj){
		if(obj == null)
			return false;
		
		return obj instanceof Model;		
	}
	
	
	
	/**
	 * Get a array with all Class<?> that the given methods return
	 * @param methods
	 * @return
	 */
	public ArrayList<Class<?>> getValueClassesFromMethods(ArrayList<Method> methods){
		ArrayList<Class<?>> classes = new ArrayList<>();
		
		for(Method m : methods){	
			classes.add(this.getMethodValueClass(m));
		}
		return classes;
		
	}

	/**
	 * Sets a value with a method object
	 */
	public boolean setMethodValue(Method m, Object value){
		m = this.getSetMethod(m);
		return super.setMethodValue(m, value);
	}
	
	public Object getMethodValue(Method m){
		m = this.getGetMethod(m);
		return super.getMethodValue(m);
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
