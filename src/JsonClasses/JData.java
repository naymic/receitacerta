package JsonClasses;

import java.util.ArrayList;
import java.util.HashMap;

import Model.Model;


public class JData {
	ArrayList<Model> data; 
	HashMap<String, Object> form;
	
	
	public ArrayList<Model> getData() {
		return data;
	}
	public void setData(ArrayList<Model> data) {
		this.data = data;
	}
	public HashMap<String, Object> getForm() {
		return form;
	}
	public void setForm(HashMap<String, Object> form) {
		this.form = form;
	} 

}
