package jrequestclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * 
 * @author micha
 *	
 * This is the main class for Requests and 
 * shows the JSon string structure of the request
 * 
 */
public class JRequest{
	
	//Controllername
	public String usecase;
	
	//Action inside the controller
	public String action;
	
	//Classanme to invoke the Model object
	public String classname;
	
	//Hasmap to get the values for the Model object
	public HashMap<String, String> data;
	
	//Used for tables with a lot of rows
	public JPageManager pagemanager;
	
	JRequest(){
		data = new HashMap<String, String>();
		pagemanager = new JPageManager();
	}
	
	public String getUsecase() {
		return usecase;
	}
	public void setUsecase(String usecase) {
		this.usecase = usecase;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public HashMap<String, String> getData() {
		return (HashMap<String,String>)data;
	}
	public void setData(HashMap<String, String> objects) {
		this.data = objects;
	}

	public JPageManager getPagemanager() {
		return pagemanager;
	}

	public void setPagemanager(JPageManager pagemanager) {
		this.pagemanager = pagemanager;
	}
	
	
}
