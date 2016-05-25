package GenericDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import DAOInterfaces.DAOInterface;
import Model.Model;
import Reflection.ReflectionDAO;
import Reflection.SqlStringUtils;
import DB.DB;

public class DAO implements DAOInterface{
	public static DAO dao = null;
	protected DB db;
	
	public static DAO getInstance(){
		if(dao == null){
			dao = new DAO();
		}
		return dao;
	}
	
	private DAO(){
		setDb(DB.getInstance());
	
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
		
	/**
	 * Insert a object into the database
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
		
		String[] colums = rd.getColums(rd.getPKs());
		
		
		try {
			stmt = getDb().getCon().prepareStatement("DELETE FROM "+ object.getTableName() +" WHERE "+ SqlStringUtils.getPrepStmtColumns(colums, "AND"));
			stmt = this.executeStatement(stmt, rd.getValues(rd.getPKs()) );
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
		String[] where = rd.getColums(rd.getPKs());
		
		try {
			Integer stmtIndex = new Integer(0);
			stmt = getDb().getCon().prepareStatement("UPDATE "+ object.getTableName() +" SET "+ SqlStringUtils.getPrepStmtColumns(colums, ",") +" WHERE "+ SqlStringUtils.getPrepStmtColumns(where, "AND"));
			stmt = this.executeStatement(stmt, rd.getValues(rd.getMethods()));
			stmt = this.executeStatement(stmt, rd.getValues(rd.getPKs()), rd.getMethods().size());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			r.addError(e.toString());
			e.printStackTrace();
		}
			
		return r;
		
		
		
	}
	
	
	public Model select(Model object){
		return object;
		
		
		
	}

	@Override
	public Return save(Model object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	

	

	

	


}
	


