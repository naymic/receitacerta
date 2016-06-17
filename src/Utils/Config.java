package Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Config {

	private static Config config = null;
	private String filepath;
	
	private Config(){
		
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
		return this.getConfigLineByName("dbhost");
		
	}
	
	public String DBPort(){
		return this.getConfigLineByName("dbport");
		
	}
	
	private String getConfigLineByName(String configName){
		try{
			BufferedReader br = new BufferedReader(new FileReader(this.getFilepath()+"config.txt"));
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
		    
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}

	public String DBDriver() {
		return this.getConfigLineByName("dbdriver");
	}

	
	
}
