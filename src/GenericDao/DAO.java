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
	
	
	private PreparedStatement setStmt(PreparedStatement stmt, int index, Object obj) throws SQLException{
		if(obj.getClass().getName().equals("java.lang.Integer")){
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
	
	private ReflectionDAO setValueFromResultSet(ReflectionDAO rd, ResultSet rs, Method m, int i){
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
		
		return rd;
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

	@Override
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
		
		String[] where = rd.getColums(rd.getGetPKs());
		
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
				for(Method m : mset){
					rd = this.setValueFromResultSet(rd, rs, m, i);
					
					i++;
				}
				i=1;
				returnList.add((Model)rd.cloneObject(rd.getObject()));
			}
			
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		return returnList;		
	}



}
	


