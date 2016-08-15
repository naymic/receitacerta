package JsonClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class JRequest{
	
	public String usecase;
	public String action;
	public String classname;
	public HashMap<String, String> data;
	
	JRequest(){
		data = new HashMap<String, String>();
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
	
	
}
