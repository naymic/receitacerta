package Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Return {
	private HashMap<String, String> errorMap;

	private boolean success;
	

	
	public Return(){
		setSuccess(true);
		errorMap = new HashMap<String, String>();
	}
	
	
	public void addError(String errorMsg){
		setSuccess(false);
		
		errorMap.put("error",errorMsg);
	}

	public void addError(String tableName, String attributeName, String errorMsg){
		setSuccess(false);
		
		errorMap.put("atrb_"+attributeName,errorMsg);
	}
	
	
	public HashMap<String, String> getErrors(){return errorMap;}
	
	
	public boolean isSuccess(){return success;}
	public void setSuccess(boolean success){
		this.success = success;
		this.errorMap = new HashMap<>();
	}
	
	
}
