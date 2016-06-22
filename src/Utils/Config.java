package Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Config {

	private boolean testDB;
	private boolean fileFound;
	public String dbhost;
	public String dbport;
	public String dbdriver;
	private String filepath;
	private String db;
	
	private static Config config = null;
	
	private Config(){
		testDB=false;
		fileFound=true;
		
		//Special for test database
		dbhost="naymic.dlinkddns.com";
		dbport="3306";
		dbdriver="mysql";
		//db="receita_prog2";
		db="receita_certa";
	}
	
	public void setTestDB(boolean b){
		this.testDB=b;
	}
	
	public static Config getInstance(){
		if(config == null){
			config = new Config();
		}
		return config;	
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	public String DBHost(){		
		//return this.dbhost;
		return this.getConfigLineByName("dbhost");
		
	}
	
	public String DBPort(){
		//return this.dbport;
		return this.getConfigLineByName("dbport");
	}
	
	public String DBDriver() {
		//return this.dbdriver;
		return this.getConfigLineByName("dbdriver");
	}
	
	public String DB() {
		//return this.dbdriver;
		return this.getConfigLineByName("db");
	}

	


	private String getConfigLineByName(String configName){
		try{	
			
				BufferedReader br = new BufferedReader(new FileReader(filepath+"/config.txt"));
				String[] config;
				String line;
				while ((line = br.readLine()) != null) {
					line = line.toLowerCase();
					configName = configName.toLowerCase();

					if(line.contains(configName) && !line.contains("#") && !line.contains("/") && !line.contains("*")){
						config = line.split("=");
						System.out.println(line);
						return config[1].trim();
					}
				}
			
		} catch(Exception e){
			System.out.println("Please create a config.txt file in the WebContent folder.\nAdd following lines:\ndbhost=naymic.dlinkddns.com\ndbport=3306\ndbdriver=mysql");
			//e.printStackTrace();
			fileFound=false;
		}

		return "";
	}


	
	
}
