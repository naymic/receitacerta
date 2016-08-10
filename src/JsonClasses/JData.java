package JsonClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.Model;


public class JData {
	String classname;

	ArrayList<Model> data; 
	HashMap<String, Object> form;
	
	
	public JData(String classname){
		this.setClassname(classname);
	}
	
	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	public List<Model> getData() {
		return data;
	}	
	
	public void setData(ArrayList<Model> data) {
		this.data = data;
	}
	public void setData(List<Model> data) {
		this.data = (ArrayList<Model>)data;
	}
	
	
	public HashMap<String, Object> getForm() {
		return form;
	}
	public void setForm(HashMap<String, Object> form) {
		this.form = form;
	} 

}
