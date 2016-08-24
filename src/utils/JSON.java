package utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;

/*
public class JSON {
	private Gson g;
	
	public JSON(){
		g = new Gson();
	}
	
	public Gson getGson(){
		return this.g;
	}
	
	public String objectJson(ReflectionDAORelation rdr, boolean isEdit){
		String[] json = ;
		for(Method m : rdr.getMethods()){
			json +=this.attributeJson(rdr, rdr.getColumnName(m), isEdit);
		}
	

		return "{\"data\":{"+json[0]+","json[1]+"}}";
	}
	
	private String[] attributeJson(ReflectionDAORelation rdr, String attributeName, boolean isEdit){
		String[] rdata = new String[2];
		String data = "{\"data\":{";
		String form = "{\"form\":{";
		
		if(rdr.isFK(rdr.getGetMethodByColumname(attributeName))){
			// Retrive all options of the Relation Table
			List<Model> list = DAORelation.getInstance().getFKRelationList(rdr, attributeName);
			
			
			try{			
				if(list.isEmpty())
					throw new Exception("List is Empty list, please entre some values to table"+ rdr.getObject().getClass());
				
				
				data += "\""+attributeName+"\":[\n";
				for(Model mod : list) {
					ReflectionDAORelation rd1 = new ReflectionDAORelation(mod);
					String[] columns = rd1.getColums(rd1.getGetMethods());
					String s1 = null;
					
					data += getGson().toJson(mod);
					String selected = "";

					if(isEdit && rdr.isFK(rdr.getGetMethodByColumname(attributeName)) && DAORelation.isSameID(rdr, attributeName, rd1.getPK()))
						selected = "selected";
					
					form += this.getGson().toJson(rd1.getObject());
				}
				
				data = data.substring(0, data.length()-2);
				data += "],\n";
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			
			data = this.getAttribute(rdr, attributeName, isEdit, false);
		}
		

		rdata[0]= data.substring(0, data.length()-2)+"}";
		rdata[1]= form.substring(0, form.length()-2)+"}";
		return rdata;
	}
	
	
	private String getAttribute(ReflectionDAORelation rdr, String attributeName, boolean getValue, boolean checkRelation){
		String json = "";
		Object value = "";
		if(getValue){
			value = rdr.getValueFromAttributeName(attributeName, checkRelation);
			if(ReflectionDAO.isModelClass(value)){
				ReflectionDAORelation rdr1 = new ReflectionDAORelation((Model)value);
				
				String json1 = "";
				for(String column : rdr1.getColums(rdr1.getGetMethods())){
					json1 += this.getSubAttribute(rdr1, column, rdr1.getValueFromAttributeName(column));
				}
				return  "\t\""+ attributeName +"\":{"+json1.substring(0, json1.length()-2)+"},\n";
			}
		}
		
		if(value == null){
			value = "";
		}
		
		json +="\t";					
		json += "\""+ attributeName +"\":\""+ value +"\",";
		json +="\n";
		
		return json;
	}
	
	
	
	private String getSubAttribute(ReflectionDAORelation rdr, String attributeName, Object value){						
		return "\""+ attributeName +"\":\""+ value +"\" ,";		
	}
	
	private String getNormalAttribue(ReflectionDAORelation rdr, String attributeName, boolean getValue){
		return this.getAttribute(rdr, attributeName, true, false);
	}
	
	private String getRelatetAttribue(ReflectionDAORelation rdr, String attributeName){
		return this.getAttribute(rdr, attributeName, true, true);
	}
	
	
	
	public String listJSON(List<Model> objectList){
		String json = "";
		
		Gson jo = new Gson();
		json = jo.toJson(objectList);
		
		return json;
	}	
	
	public String returnConstruct(JReturn r){
		Gson g = new Gson();
		return g.toJson(r);
	}
	
	
	
	
}
*/
import dao.DAORelation;
import jsonclasses.JReturn;
import model.Model;
import reflection.ReflectionDAO;
import reflection.ReflectionDAORelation;