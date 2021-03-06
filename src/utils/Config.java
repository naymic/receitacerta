package utils;

import java.io.BufferedReader;
import java.io.FileReader;

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
		this.setFilepath("/home/micha/workspace/receitacertabackend/WebContent/");
	}
	
	public boolean isTestDB(){
		return this.testDB;
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


	/* Database configuration */
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

	
	/* Message Service configuration */
	public String msgWS(){
		return this.getConfigLineByName("msgws");
	}




	private String getConfigLineByName(String configName){
		String result = "";
		
		
			try{				
				BufferedReader br = new BufferedReader(new FileReader(filepath+"/config.txt"));
				String[] config;
				String line;
				while ((line = br.readLine()) != null) {
				
					configName = configName.toLowerCase();

					//Multiple line comments
					if(line.contains("/*") && !line.contains("*/")){
						while((line = br.readLine()) != null)
							if(line.contains("*/"))
								break;
					}

					//One line comments
					if(line.contains(configName) && !line.contains("#") && !line.startsWith("//") && !line.contains("*")){
						config = line.split("=");
						
						return config[1].trim();
					}
				}

			} catch(Exception e){
				System.out.println("Please create a config.txt file in the WebContent folder.\nAdd following lines:\ndbhost=naymic.dlinkddns.com\ndbport=3306\ndbdriver=mysql\n"+filepath+"/config.txt");
				//e.printStackTrace();
				fileFound=false;
			}


		
		System.out.println(result);
		return result;
	}

	
	
	

}
