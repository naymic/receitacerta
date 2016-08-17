package JsonClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.Model;


public class JData {
	String classname;

	ArrayList<Model> data; 
	ArrayList<JData> form;
	
	public JData(){
		this.setDataList(new ArrayList<Model>());
	}
	
	
	public JData(String classname){
		this.setClassname(classname);
	}
	
	public String getClassname() {
		return classname;
	}

	private void setClassname(String classname) {
		this.classname = classname;
	}
	
	public List<Model> getData() {
		return data;
	}	
	
	public void setDataList(List<Model> data) {
		this.setClassname(data.get(0).getClass().getSimpleName());
		this.data = (ArrayList<Model>)data;
	}
	
	private void setDataList(ArrayList<Model> list){
		this.data = list;
	}
	
	public void setDataObject(Model object){
		this.setClassname(object.getClass().getSimpleName());
		data.add(object);
	}
	
	
	public ArrayList<JData> getForm() {
		return form;
	}
	private void setForm(ArrayList<JData> form) {
		this.form = form;
	} 
	
	public void addFormItem(ArrayList<JData> list){
		this.setForm(new ArrayList<JData>());
		this.setForm(list);
	}

}
