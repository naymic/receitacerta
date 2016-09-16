package db;


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


	public DB() {
		this.etablishConnection();
	}

	public static DB getInstance(){

		if(db == null){
			db = new DB();	
		}

		return db;
	}
	


	
	
	private void etablishConnection(){
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("JDBC driver loaded.");

			String host;
			String port;
			String driver;
			String db;
			
			if(!Config.getInstance().isTestDB()){
				host = Config.getInstance().DBHost();
				port = Config.getInstance().DBPort();
				driver = Config.getInstance().DBDriver();
				db = Config.getInstance().getDB();
				//con = DriverManager.getConnection("jdbc:mysql://naymic.dlinkddns.com:3306/receita_prog2?characterEncoding=UTF-8", "receita_certa", "nosestamosonline75113");
				
				con = DriverManager.getConnection("jdbc:"+driver+"://"+host+":"+port+"/"+db+"?characterEncoding=utf8", "receita_certa", "nosestamosonline75113");
				
				
			}else{
				host = Config.getInstance().DBHost();
				port = Config.getInstance().DBPort();
				driver = Config.getInstance().DBDriver();

				con = DriverManager.getConnection("jdbc:"+driver+"://"+host+":"+port+"/testDB?characterEncoding=utf8", "receita_certa", "nosestamosonline75113");
				db = "testDB";
			}
			
			System.out.println("DB conection etablished to: "+db+"@"+host+":"+port);
			setCon(con);
			
		}catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}catch (SQLException e) {
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
			System.out.println("DAO SQL problem - SELECT.");
			System.out.println(e.toString());
		}

		return null;
	}


}
