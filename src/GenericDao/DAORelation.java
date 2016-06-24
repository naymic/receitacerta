package GenericDao;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import Model.Model;
import Reflection.ReflectionDAORelation;

public class DAORelation extends DAO{

	private static DAORelation dr = null;
	
	public static DAORelation getInstance(){
		if(dr == null){
			dr = new DAORelation(false);
		}
		return dr;
	}
	
	public static DAORelation getTestInstance(){
		if(dr == null){
			dr = new DAORelation(true);
		}
		return dr;
	}
	
	private DAORelation(boolean istestDB){
		super(istestDB);	
	};
	
	
	/**
	 * Select rows in a table 
	 * if all attributes are null all rows are selected
	 * if one or more attributes are set rows are selected by them
	 * @param	object 		Model				Object to set table and filter 
	 * @return  returnList	ArrayList<Model> 	List of Model objects 
	 */
	public ArrayList<Model> select(Model object){
		ReflectionDAORelation rdr = new ReflectionDAORelation(object);
		

		ArrayList<Method> mget = new ArrayList<>();
		ArrayList<Method> mset = rdr.getSetMethods();
		ArrayList<Method> where = new ArrayList<>();
		
		
		//Retrieves a list for the get Methods in the same order than the set Methods are
		for(Method m : mset){
			mget.add(rdr.getGetMethod(m));
		}
		
		//Retrieves all values not null of the object and attributes it to a method list 
		for(Method m : mget){
		
			if(rdr.getMethodRelationId(m) != null ){
				
				where.add(m);
			}
		}
		
		//Return all objects of the executed sql query
		return this.getObjectsFromRS(rdr, rdr.prepareSelectSqlString(mget, where), mget, mset, where);
	}
	
	
	public List<Model> getFKRelationList(ReflectionDAORelation rdr, String fk_attributeName){
		Method m = rdr.getGetMethodByColumname(fk_attributeName);
		Model obj = (Model) ReflectionDAORelation.instanciateObjectByName(rdr.getMethodValueClass(m));
			return  DAO.getInstance().select(obj);
	}
	
	
	public static boolean isSameID(ReflectionDAORelation rdr, String columnName ,Object inheritID)throws Exception{
		
		if(rdr.getValueFromAttributeName(columnName) == null){
			List<Model> list = DAORelation.getInstance().select(rdr.getObject());
			if(list.size() > 1)
				throw new Exception("Object need a primary key "+ rdr.getObject().getClass().getName());
			rdr.setObject(list.get(0));
		}
		
		Model relObject = (Model)rdr.getValueFromAttributeName(columnName);
		ReflectionDAORelation rdr1 = new ReflectionDAORelation(relObject);
		return rdr1.getPK().equals(inheritID);
	}
	

	
}
