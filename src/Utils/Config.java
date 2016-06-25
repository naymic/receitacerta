package Utils;

import java.io.BufferedReader;
import java.io.FileReader;

import Reflection.GenericReflection;

public class Config {

	private boolean testDB;
	private boolean fileFound;
	private String filepath;
	
	private String dbhost;
	private String dbport;
	private String dbdriver;
	private String db;
	
	private static Config config = null;

	private Config(){
		testDB=false;
		fileFound=true;
	}

	public void setTestDB(boolean b){
		
		//Set Static values for JUnit Tests
		this.testDB=b;
		dbhost="naymic.dlinkddns.com";
		dbport="3306";
		dbdriver="mysql";
		db="testDB";
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

	public String getDB() {
		//return this.dbdriver;
		return this.getConfigLineByName("db");
	}




	private String getConfigLineByName(String configName){
		String result = "";
		
		
		if(!this.testDB){
			try{				
				BufferedReader br = new BufferedReader(new FileReader(filepath+"/config.txt"));
				String[] config;
				String line;
				while ((line = br.readLine()) != null) {
					line = line.toLowerCase();
					configName = configName.toLowerCase();

					//Multiple line comments
					if(line.contains("/*") && !line.contains("*/")){
						while((line = br.readLine()) != null)
							if(line.contains("*/"))
								break;
					}

					//One line comments
					if(line.contains(configName) && !line.contains("#") && !line.contains("/") && !line.contains("*")){
						config = line.split("=");
						
						return config[1].trim();
					}
				}

			} catch(Exception e){
				System.out.println("Please create a config.txt file in the WebContent folder.\nAdd following lines:\ndbhost=naymic.dlinkddns.com\ndbport=3306\ndbdriver=mysql");
				//e.printStackTrace();
				fileFound=false;
			}


			if(result.length() == 0 || result == null){
				result =  getStandartConfig(configName);
			}
		}else{
			result = getStandartConfig(configName);
		}
		System.out.println(result);
		return result;
	}
	
	
	private String getStandartConfig(String configName){
		GenericReflection gr = new GenericReflection(this);
		return (String) gr.getFieldValue(configName);
	}
}
