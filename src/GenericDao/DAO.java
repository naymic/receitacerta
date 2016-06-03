package GenericDao;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DAOInterfaces.DAOInterface;
import Model.Model;
import Reflection.ReflectionDAO;
import Utils.Return;
import Utils.SqlStringUtils;
import DB.DB;



/**
 * Singleton class for Default acess object 
 * for Database transactions
 * 
 * @author naymic
 *
 */

public class DAO implements DAOInterface{
	public static DAO dao = null;
	protected DB db;
	
	public static DAO getInstance(){
		if(dao == null){
			dao = new DAO(false);
		}
		return dao;
	}
	
	public static DAO getTestInstance(){
		if(dao == null){
			dao = new DAO(true);
		}		
		return dao;
	}
	
	private DAO(boolean b){
		if(!b)
			setDb(DB.getInstance());
		else
			setDb(DB.getTestInstance());
	
	}
	
	public DB getDb() {return db;}
	public void setDb(DB db) {this.db = db;}
	
	/**
	 * Executes the SQL Query securly
	 * @param stmt
	 * @param fields
	 * @throws SQLException
	 */
	private PreparedStatement executeStatement(PreparedStatement stmt, Object[] objects, Integer index) throws SQLException{
		Object obj;
		
		for(int i = 0 ; i<objects.length; i++){
				obj = objects[i];
			
				stmt = this.setStmt(stmt, i+index+1, obj);
		}
		return stmt;
	}
	
	private PreparedStatement executeStatement(PreparedStatement stmt, Object[] objects) throws SQLException{
		return this.executeStatement(stmt, objects, new Integer(0));
	}
	
	
	
	
	/**
	 * Insert a object into the database
	 * @param object
	 * @return
	 */
	public Return insert(Model object){
		Return r = new Return();
		PreparedStatement stmt;
		ReflectionDAO rd = new ReflectionDAO(object);
		
		String colums = SqlStringUtils.getCommaString(rd.getColums(rd.getMethods()));
		String preparedStmt = SqlStringUtils.getCommaString(rd.getPreparedValues(rd.getMethods()));
		
		
		try {
			
			stmt = getDb().getCon().prepareStatement("INSERT INTO "+ object.getTableName() +" (" + colums +") VALUES ("+ preparedStmt +")");
			stmt = this.executeStatement(stmt, rd.getValues(rd.getMethods()));
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			r.addError(e.toString());
			e.printStackTrace();
		}
		
		
		
		return r;
	}
	
	/**
	 * Delete a object in the database
	 * @param object
	 * @return
	 */
	public Return delete(Model object){
		Return r = new Return();
		PreparedStatement stmt = null;
		ReflectionDAO rd = new ReflectionDAO(object);
		
		String[] colums = rd.getColums(rd.getGetPKs());
		
		
		try {
			stmt = getDb().getCon().prepareStatement("DELETE FROM "+ object.getTableName() +" WHERE "+ SqlStringUtils.getPrepStmtColumns(colums, "AND"));
			stmt = this.executeStatement(stmt, rd.getValues(rd.getGetPKs()) );
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			r.addError(e.toString());
			e.printStackTrace();
		}
			
		return r;
	}
	
	/**
	 * Update a object in the database
	 * @param object
	 * @return
	 */
	public Return update(Model object){
		Return r = new Return();
		PreparedStatement stmt = null;
		ReflectionDAO rd = new ReflectionDAO(object);
		
		String[] colums = rd.getColums(rd.getMethods());
		String[] where = rd.getColums(rd.getGetPKs());
		
		try {
			Integer stmtIndex = new Integer(0);
			stmt = getDb().getCon().prepareStatement("UPDATE "+ object.getTableName() +" SET "+ SqlStringUtils.getPrepStmtColumns(colums, ",") +" WHERE "+ SqlStringUtils.getPrepStmtColumns(where, "AND"));
			stmt = this.executeStatement(stmt, rd.getValues(rd.getMethods()));
			stmt = this.executeStatement(stmt, rd.getValues(rd.getGetPKs()), rd.getMethods().size());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			r.addError(e.toString());
			e.printStackTrace();
		}
			
		return r;
		
		
		
	}

	/**
	 * 
	 */
	public Return save(Model object) {
		if(this.existModel(object)){
			return this.update(object);
		}else{
			return this.insert(object);
		}
		
	}
	
	
	public boolean existModel(Model object){
		Return r = new Return();
		PreparedStatement stmt = null;
		ReflectionDAO rd = new ReflectionDAO(object);
		Method m = null;
		
		String[] where = rd.getColums(rd.getGetPKs());
		
		this.ckeckNullPK(rd);
			
		
		
		try {
			stmt = getDb().getCon().prepareStatement("SELECT null FROM "+ object.getTableName() +" WHERE "+ SqlStringUtils.getPrepStmtColumns(where, "AND"));
			stmt = this.executeStatement(stmt, rd.getValues(rd.getGetPKs()));
			
			ResultSet rs = stmt.executeQuery();			
			
			if(rs.next()){
				rs.close();
				return true;
			}else{
				rs.close();
				return false;
			}
			
		} catch (SQLException e) {
			r.addError(e.toString());
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
		Model newModel = null;
		ReflectionDAO rd = new ReflectionDAO(object);
		PreparedStatement stmt = null;

		ArrayList<Method> mget = new ArrayList<>();
		ArrayList<Method> mset = rd.getSetMethods();
		ArrayList<Method> where = new ArrayList<>();
		ArrayList<Model> returnList = new ArrayList<>();
		
		
		for(Method m : mset){
			mget.add(rd.getGetMethod(m));
		}
		
		
		for(Method m : mget){
		
			if(rd.getMethodValue(m) != null ){
				where.add(m);
			}
		}
		
		String[] colums = rd.getColums(mget);
		String[] _where = rd.getColums(where);
		String __where = "";
		if(where.size() > 0){
			__where = "WHERE " + SqlStringUtils.getPrepStmtColumns(_where, " AND");
		}
		
		
		try {
			stmt = getDb().getCon().prepareStatement("SELECT "+ SqlStringUtils.getCommaString(colums) +" FROM "+ object.getTableName() +" "+ __where);
			stmt = this.executeStatement(stmt, rd.getValues(where));
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


	/*
	 *  Helper Classes 
	 */
	
	/**
	 * Receives a column index and replaces the ? in the prepared sql
	 *  statement through the given object
	 * @param stmt		Prepared statement object
	 * @param index		Column index
	 * @param obj		Value to replace the ?
	 * @return 			New stmt with one ? replaced
	 * @throws SQLException
	 */
	private PreparedStatement setStmt(PreparedStatement stmt, int index, Object obj) throws SQLException{
			
		if(obj == null){
			stmt.setNull(index, java.sql.Types.VARCHAR, null);
		}else if(obj.getClass().getName().equals("java.lang.Integer")){
			stmt.setInt(index, (int)obj);
		}else if(obj.getClass().getName().equals("java.lang.Double")){
			stmt.setDouble(index, (Double)obj);
		}else if(obj.getClass().getName().equals("java.lang.String")){
			stmt.setString(index, (String)obj);
		}else if(obj.getClass().getName().equals("java.lang.Float")){
			stmt.setFloat(index, (Float)obj);
		}else if(obj.getClass().getName().equals("java.sql.Date")){
			stmt.setDate(index, (java.sql.Date)obj);
		}
		
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
			if(rd.getMethodValueClass(m).toString().contains("java.lang.Integer")){
					rd.setMethodValue(m.getName(), rs.getInt(i));
			}else if(rd.getMethodValueClass(m).toString().contains("java.lang.Double")){
				rd.setMethodValue(m.getName(), rs.getDouble(i));
			}else if(rd.getMethodValueClass(m).toString().contains("java.lang.String")){
				rd.setMethodValue(m.getName(), rs.getString(i));
			}else if(rd.getMethodValueClass(m).toString().contains("java.lang.Float")){
				rd.setMethodValue(m.getName(), rs.getFloat(i));
			}else if(rd.getMethodValueClass(m).toString().contains("java.sql.Date")){
				rd.setMethodValue(m.getName(), rs.getDate(i));
			}
		} catch (SQLException e) {
			System.out.println("Error by set value to object: "+rd.getObject().toString()+" and method:"+ m.getName());
			e.printStackTrace();
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
		rd.setMethodValue(m1.getName(), this.getMaxID(model.getTableName(), rd.getColumnName(m)));		
	}
	
	/**
	 * Returns the highest int number of a column in a table 
	 * @param tableName
	 * @param column
	 * @return int 
	 */
	private int getMaxID(String tableName, String column){
		int i = -1;
		
		
		ResultSet rs = this.getDb().getInstance().selectQuery("SELECT MAX("+column+") FROM "+tableName+" WHERE 1");
		try {
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

}
	


