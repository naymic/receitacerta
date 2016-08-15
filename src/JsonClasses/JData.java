package JsonClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.Model;


public class JData {
	String classname;

	ArrayList<Model> data; 
	HashMap<String, ArrayList<Model>> form;
	
	JData(){
		this.setDataList(new ArrayList<Model>());
		this.setForm(new HashMap<String, ArrayList<Model>>());
	}
	
	
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
	
	public void setDataList(List<Model> data) {
		this.data = (ArrayList<Model>)data;
	}
	
	public void setDataList(ArrayList<Model> list){
		this.data = list;
	}
	
	public void setDataObject(Model object){
		data.add(object);
	}
	
	
	public HashMap<String, ArrayList<Model>> getForm() {
		return form;
	}
	private void setForm(HashMap<String, ArrayList<Model>> form) {
		this.form = form;
	} 
	
	public void addFormItem(String objectName, ArrayList<Model> list){
		this.getForm().put(objectName, list);
	}

}
