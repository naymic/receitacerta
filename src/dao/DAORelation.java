package dao;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import model.Model;
import reflection.ReflectionDAORelation;

public class DAORelation extends DAO{

	private static DAORelation dr = null;
	
	public static DAORelation getInstance(){
		if(dr == null){
			dr = new DAORelation();
		}
		return dr;
	}
	
	protected DAORelation(){
		super();	
	};
	
	
	/**
	 * Select rows in a table ( = <parameter>)
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
		
		
		prepareSelect(rdr, mget, mset, where);
		
		//Return all objects of the executed sql query

			return this.getObjectsFromRS(rdr, rdr.prepareSelectSqlString(mget, where), mget, mset, where, false);
	}
	
	
	
	/**
	 * Search rows in a table (LIKE %<parameter>%)
	 * if all attributes are null all rows are selected
	 * if one or more attributes are set rows are selected by them
	 * @param	object 		Model				Object to set table and filter 
	 * @return  returnList	ArrayList<Model> 	List of Model objects 
	 */
	public ArrayList<Model> search(Model object){
		ReflectionDAORelation rdr = new ReflectionDAORelation(object);
		

		ArrayList<Method> mget = new ArrayList<>();
		ArrayList<Method> mset = rdr.getSetMethods();
		ArrayList<Method> where = new ArrayList<>();
		
		
		prepareSelect(rdr, mget, mset, where);
		
		//Return all objects of the executed sql query

		return this.getObjectsFromRS(rdr, rdr.prepareSarchSqlString(mget, where), mget, mset, where, true);
	}
	
	
	
	/**
	 * Prepare methods in variables for select and search Methods
	 * @param rdr
	 * @param fk_attributeName
	 * @return void
	 */
	protected void prepareSelect(ReflectionDAORelation rdr, ArrayList<Method> mget, ArrayList<Method> mset,
			ArrayList<Method> where) {
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
	}
	
	/**
	 * Return a list of all FK object of a given fk attributename
	 * @param rdr
	 * @param fk_attributeName
	 * @return
	 */
	public List<Model> getFKRelationList(ReflectionDAORelation rdr, String fk_attributeName){
		Method m = rdr.getGetMethodByColumname(fk_attributeName);
		Model obj = (Model) ReflectionDAORelation.instanciateObjectByName(rdr.getMethodValueClass(m));
			return  DAO.getInstance().select(obj);
	}
	
	
	/**
	 * Find out if in a subobject exist the same id of the object
	 * @param rdr
	 * @param columnName
	 * @param inheritID
	 * @return
	 * @throws Exception
	 */
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
	
	
	/**
	 * Get a list of the IDs of the given Model list
	 * @param modelList
	 * @return Array<Object> PK values 
	 */
	protected ArrayList<String> getPKListFromModelList(ArrayList<Model> modelList){
		ArrayList<String> idRecipeList = new ArrayList<>();
		
		for(Model model : modelList){
			ReflectionDAORelation rdr = new ReflectionDAORelation(model);
			idRecipeList.add(rdr.getPK().toString());			
		}
		
		return idRecipeList;
		
		
	}
	

	
}
