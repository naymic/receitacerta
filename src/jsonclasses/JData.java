package jsonclasses;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Model;


public class JData {
	String classname;

	ArrayList<Model> data; 
	HashMap<String, JDataType> dataType;
	
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
		if(data.size() > 0){this.setClassname(data.get(0).getClass().getSimpleName());
			this.data = (ArrayList<Model>)data;
		}else{
			this.data = new ArrayList<>();
		}
	}
	
	private void setDataList(ArrayList<Model> list){
		this.data = list;
	}
	
	public void setDataObject(Model object){
		this.setClassname(object.getClass().getSimpleName());
		data.add(object);
	}
	
	
	public void setDataTypes(ArrayList<Field> fieldList){
		this.dataType = new HashMap<>();
		
		for(Field f: fieldList){
			JDataType djt = new JDataType(f);
			dataType.put(f.getName(), djt);
		}
	}
	
}
