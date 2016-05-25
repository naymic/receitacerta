package DB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {


	public Connection con;

	//Singleton
	private static DB db = null;


	public static DB getInstance(){

		if(db == null){
			db = new DB();	
		}

		return db;
	}


	private DB(){		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("JDBC driver carregado.");
			
			
			
		}
		catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
		
		
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/receita_certa", "receita_certa", "nosestamosonline75113");
			setCon(con);

		}
		catch (SQLException e) {
			System.out.println("Problema SQL." + e.toString());
		}
		catch (Exception e) {
			System.out.println("Exceção geral !!!!");
		}
	}
	
	public Connection getCon(){return con;}
	public void setCon(Connection con){this.con = con;}
	
	
	
	public boolean updateQuery(String sql){
		int recordAffected = 0;
		Connection con = getCon();
		Statement s;
		try {
			s = con.createStatement();
			recordAffected = s.executeUpdate(sql);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		if (recordAffected == 1)
			return true;
		else 
			return false;
	}


	public ResultSet selectQuery(String sql){

		try{
			PreparedStatement  stm = con.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			//stm.close();
			return rs;

		} catch (SQLException e){
			System.out.println("Problema Dao SQL - consultar.");
			System.out.println(e.toString());
		}

		return null;
	}



}
