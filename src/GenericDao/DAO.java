package GenericDao;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Converter.GenericConverter;
import Model.Model;
import Reflection.ReflectionDAO;
import Utils.Return;
import Utils.StringUtils;
import DB.DB;
import Interfaces.IDAO;



/**
 * Singleton class for Default acess object 
 * for Database transactions
 * 
 * @author naymic
 *
 */

public class DAO implements IDAO{
	public static DAO dao = null;
	protected DB db;
	
	/**
	 * Get a normal singleton instance of DAO 
	 * @return
	 */
	public static DAO getInstance(){
		if(dao == null){
			dao = new DAO(false);
		}
		return dao;
	}
	
	/**
	 * Get a singleton instance of DAO for access to the TestDatabase
	 * @return
	 */
	public static DAO getTestInstance(){
		if(dao == null){
			dao = new DAO(true);
		}		
		return dao;
	}
	
	/**
	 * DAO constructor 
	 * @param b 	boolen	[true = set the test database | false = set the normal database connection] 
	 */
	protected DAO(boolean b){
		if(!b)
			setDb(DB.getInstance());
		else
			setDb(DB.getTestInstance());
	
	}
	
	/**
	 * Return the Database
	 * @return
	 */
	public DB getDb() {return db;}
	
	/**
	 * Sets the database
	 * @param db
	 */
	public void setDb(DB db) {this.db = db;}
	
	/**
	 * Executes the SQL Query securely
	 * @param stmt
	 * @param fields
	 * @throws SQLException
	 */
	private PreparedStatement executeStatement(PreparedStatement stmt, Object[] objects, Integer index, boolean search) throws SQLException{
		Object obj;
		
		for(int i = 0 ; i<objects.length; i++){
				obj = objects[i];
			
				stmt = this.setStmt(stmt, i+index+1, obj, search);
		}
		return stmt;
	}
	
	/**
	 * Executes the SQL Query securely beginning with a 0 for column index
	 * @param stmt
	 * @param objects
	 * @return
	 * @throws SQLException
	 */
	private PreparedStatement executeStatement(PreparedStatement stmt, Object[] objects, boolean search) throws SQLException{
		return this.executeStatement(stmt, objects, new Integer(0), search);
	}
	
	
	
	
	/**
	 * Insert a object into the database
	 * @param object
	 * @param r2 
	 * @return
	 */
	public Return insert(Model object, Return r){
		PreparedStatement stmt;
		ReflectionDAO rd = new ReflectionDAO(object);
		
		String colums = StringUtils.getCommaString(rd.getColums(rd.getMethods()));
		String preparedStmt = StringUtils.getCommaString(rd.getPreparedValues(rd.getMethods()));
		
		
		try {
			
			stmt = getDb().getCon().prepareStatement("INSERT INTO "+ object.getTableName() +" (" + colums +") VALUES ("+ preparedStmt +")");
			stmt = this.executeStatement(stmt, rd.getValues(rd.getMethods()), false);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			r.addSimpleError(e.toString());
			e.printStackTrace();
		}
		
		
		
		return r;
	}
	
	/**
	 * Delete a object in the database
	 * @param object
	 * @return
	 */
	public Return delete(Model object, Return r){
		PreparedStatement stmt = null;
		ReflectionDAO rd = new ReflectionDAO(object);
		
		String[] colums = rd.getColums(rd.getGetPKs());
		
		
		try {
			stmt = getDb().getCon().prepareStatement("DELETE FROM "+ object.getTableName() +" WHERE "+ StringUtils.getPrepStmtColumns(colums, "AND", false));
			stmt = this.executeStatement(stmt, rd.getValues(rd.getGetPKs()), false);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			r.addSimpleError(e.toString());
			e.printStackTrace();
		}
			
		return r;
	}
	
	/**
	 * Update a object in the database
	 * @param object
	 * @param r2 
	 * @return
	 */
	public Return update(Model object, Return r){
		PreparedStatement stmt = null;
		ReflectionDAO rd = new ReflectionDAO(object);
		
		String[] colums = rd.getColums(rd.getMethods());
		String[] where = rd.getColums(rd.getGetPKs());
		
		try {
			stmt = getDb().getCon().prepareStatement("UPDATE "+ object.getTableName() +" SET "+ StringUtils.getPrepStmtColumns(colums, ",", false) +" WHERE "+ StringUtils.getPrepStmtColumns(where, "AND", false));
			stmt = this.executeStatement(stmt, rd.getValues(rd.getMethods()), false);
			stmt = this.executeStatement(stmt, rd.getValues(rd.getGetPKs()), rd.getMethods().size(), false);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			r.addSimpleError(e.getMessage());
			e.printStackTrace();
		}
			
		return r;
		
		
		
	}

	/**
	 * Saves a object in the database
	 * if the object exist then just updates it
	 * else insert a new line in the database
	 * @param r 
	 */
	public Return save(Model object, Return r) {
		if(this.existModel(object, r)){
			return this.update(object, r);
		}else{
			return this.insert(object, r);
		}
		
	}
	
	/**
	 * Finds out if a object exist already in the database
	 * @param object
	 * @return boolean [true = object exist in the database | false = object don't exist in the database]
	 */
	public boolean existModel(Model object, Return r){
		PreparedStatement stmt = null;
		ReflectionDAO rd = new ReflectionDAO(object);
		
		String[] where = rd.getColums(rd.getGetPKs());
		
		//Checks if a primary key is null, then looks for the next id
		this.ckeckNullPK(rd);
			
		
		try {
			stmt = getDb().getCon().prepareStatement("SELECT null FROM "+ object.getTableName() +" WHERE "+ StringUtils.getPrepStmtColumns(where, "AND", false));
			stmt = this.executeStatement(stmt, rd.getValues(rd.getGetPKs()), false);
			
			ResultSet rs = stmt.executeQuery();			
			
			if(rs.next()){
				rs.close();
				return true;
			}else{
				rs.close();
				return false;
			}
			
		} catch (SQLException e) {
			r.addSimpleError(e.toString());
			e.printStackTrace();
		}
			
		return false;
	}

	
	/**
	 * Select rows in a table 
	 * if all attributes are null all rows are selected
	 * if one or more attributes are set rows are selected by them
	 * @param	object 		Model				Object to set table and filter 
	 * @return  returnList	ArrayList<Model> 	List of Model objects 
	 */
	public ArrayList<Model> select(Model object){
		ReflectionDAO rd = new ReflectionDAO(object);
		

		ArrayList<Method> mget = new ArrayList<>();
		ArrayList<Method> mset = rd.getSetMethods();
		ArrayList<Method> where = new ArrayList<>();
		
		
		//Retrieves a list for the get Methods in the same order than the set Methods are
		for(Method m : mset){
			mget.add(rd.getGetMethod(m));
		}
		
		//Retrieves all values not null of the object and attributes it to a method list 
		for(Method m : mget){
		
			if(rd.getMethodValue(m) != null ){
				where.add(m);
			}
		}
		
		//Return all objects of the executed sql query
		return this.getObjectsFromRS(rd, rd.prepareSelectSqlString(mget, where, false), mget, mset, where, false);
	}


	/*
	 *  Helper Classes 
	 */
	
	/**
	 * Get a list of all objects received from the result set
	 * @param rd	ReflectionDAO
	 * @param sql 	String 			String with the whole prepared Sql
	 * @param mget	
	 * @param mset
	 * @param where
	 * @param search 
	 * @return ArrayList<Model> return a ArrayList with Model objects
	 */
	protected ArrayList<Model> getObjectsFromRS(ReflectionDAO rd, String sql, ArrayList<Method> mget, ArrayList<Method> mset, ArrayList<Method> where, boolean search){
		Model object = (Model)rd.getObject();
		Model newModel = null;
		PreparedStatement stmt = null;
		ArrayList<Model> returnList = new ArrayList<>();
		
		try {
			stmt = getDb().getCon().prepareStatement(sql ); 
			stmt = this.executeStatement(stmt, rd.getValues(where), search);
			
			ResultSet rs = stmt.executeQuery();
			int i = 1;
			while(rs.next()){
				rd.setObject(rd.cloneObject(object));
				for(Method m : mset){
					newModel = this.setValueFromResultSet(rd, rs, m, i);
					
					i++;
				}
				i=1;
				returnList.add(newModel);
				
			}
			
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		return returnList;	
	}
	
	/**
	 * Receives a column index and replaces the ? in the prepared sql
	 *  statement through the given object
	 * @param stmt		Prepared statement object
	 * @param index		Column index
	 * @param obj		Value to replace the ?
	 * @return 			New stmt with one ? replaced
	 * @throws SQLException
	 */
	private PreparedStatement setStmt(PreparedStatement stmt, int index, Object obj, boolean search) throws SQLException{
	
		if(obj == null){
			stmt.setNull(index, java.sql.Types.VARCHAR, null);
			return stmt;
		}else if(obj.getClass().getName().contains("Model.")){
			try{
				obj = GenericConverter.convert(Integer.class, obj);
			}catch(Exception e){
				System.out.println("Error in DAO setStmt: "+ e.getMessage());
			}
		}
		
		if(!search)
			stmt.setObject(index, obj);
		else
			stmt.setObject(index, "%"+obj+"%");
		
		return stmt;
	}
	
	
	
	/**
	 * Set one value of an Model object through the given value by a sql ResultSet
	 * @param rd		ReflectionDAO with the object inside to set attributes
	 * @param rs		Actual Result Set
	 * @param m			Set method 
	 * @param i			ResultSetcolumn index
	 * @return			Model Object 
	 */
	private Model setValueFromResultSet(ReflectionDAO rd, ResultSet rs, Method m, int i){
		
		try {
			Object obj = GenericConverter.convert(rd.getMethodValueClass(m), rs.getObject(i));
			rd.setMethodValue(m.getName(), obj);
			
			
		} catch (Exception e) {
			System.out.println("Error by set value to object: "+rd.getObject().toString()+" and method:"+ m.getName());
			//e.printStackTrace();
		}
		
		return (Model)rd.getObject();
	}
	
	/**
	 * Checks if a primary key (pk) is null
	 * @param rd		ReflectionDAO with the object inside
	 * @return boolean 	[true = all pk's are set | false = one or more pk's are null]
	 */
	private void ckeckNullPK(ReflectionDAO rd){
		ArrayList<Method> getPKs = rd.getGetPKs();
		for (int i = 0; i < getPKs.size(); i++) {
			Method m = getPKs.get(i);
			if(rd.isValueNull(m)){
				this.setPK(rd, m);
			}
		}
	}
	
	/**
	 * Sets a value to a primary key where before was null
	 * @param rd
	 * @param m
	 */
	private void setPK(ReflectionDAO rd, Method m){
		Method m1 = rd.getSetMethod(m);
		Model model = (Model)rd.getObject();
		int i = this.getMaxID(model.getTableName(), rd.getColumnName(m));
		rd.setMethodValue(m1.getName(), i+1);		
		
	}
	
	/**
	 * Returns the highest int number of a column in a table 
	 * @param tableName
	 * @param column
	 * @return int 
	 */
	private int getMaxID(String tableName, String column){
	
		PreparedStatement stmt = null;
		String sql = "SELECT MAX("+column+") as max FROM "+tableName;
		
	
		try {
			stmt = getDb().getCon().prepareStatement(sql); 
			ResultSet rs = stmt.executeQuery();			
			
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

}
	


