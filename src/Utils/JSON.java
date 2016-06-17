package Utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import GenericDao.DAO;
import GenericDao.DAORelation;
import Model.Model;
import Reflection.ReflectionDAO;
import Reflection.ReflectionDAORelation;

public class JSON {
	
	
	public String objectJson(ReflectionDAORelation rdr, boolean isEdit){
		String json ="";
		for(Method m : rdr.getMethods()){
			json +=this.attributeJson(rdr, rdr.getColumnName(m), isEdit);
		}
	

		return "{\"dados\":{"+json.substring(0, json.length()-2)+"}}";
	}
	
	private String attributeJson(ReflectionDAORelation rdr, String attributeName, boolean isEdit){
		String json = "";
		Model obj = rdr.getObject();
		
		
		if(rdr.isFK(rdr.getGetMethodByColumname(attributeName))){
			// Retrive all options of the Relation Table
			List<Model> list = DAORelation.getInstance().getFKRelationList(rdr, attributeName);
			
			
			try{			
				if(list.isEmpty())
					throw new Exception("List is Empty list, please entre some values to table"+ rdr.getObject().getClass());
				
				
				json += "\""+attributeName+"\":[\n";
				for(Model mod : list) {
					ReflectionDAORelation rd1 = new ReflectionDAORelation(mod);
					String[] columns = rd1.getColums(rd1.getGetMethods());
					String s1 = null;
					
					json +="\t{";
					for(String s : columns){
						
						if(!rd1.isRequired(rd1.getGetMethodByColumname(s))){
							s1 = "else";
						}else if(!s.equalsIgnoreCase("id") ){
							s1 = "label";
						}else{
							s1 = "id";
						}
						
						json += "\""+s1+"\":\""+rd1.getValueFromAttributeName(s)+"\",";
					}
					String selected = "";

					if(isEdit && rdr.isFK(rdr.getGetMethodByColumname(attributeName)) && DAORelation.isSameID(rdr, attributeName, rd1.getPK()))
						selected = "selected";
					
					json +="\"selected\":\""+ selected +"\"},\n";
				}
				
				json = json.substring(0, json.length()-2);
				json += "],\n";
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			
			json = this.getAttribute(rdr, attributeName, isEdit, false);
		}
		

		
		return json;
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
		String json = "";
		Method m = rdr.getGetMethodByColumname(attributeName);
		
		if(rdr.isPK(m))
			attributeName = "id";
		else if(rdr.isRequired(m))
			attributeName = "label";
		else 
			attributeName = "else";
						
		return "\""+ attributeName +"\":\""+ value +"\" ,";		
	}
	
	private String getNormalAttribue(ReflectionDAORelation rdr, String attributeName, boolean getValue){
		return this.getAttribute(rdr, attributeName, true, false);
	}
	
	private String getRelatetAttribue(ReflectionDAORelation rdr, String attributeName){
		return this.getAttribute(rdr, attributeName, true, true);
	}
	
	
	
	public String listJSON(List<Model> objecList){
		String json = "";
		
		for(Model object : objecList){
			ReflectionDAORelation rdr = new ReflectionDAORelation(object);
			
			json +=",{";
			
			for(Method m : rdr.getMethods()){
				json +=this.getAttribute(rdr, rdr.getColumnName(m), true, false);
			}
			json = json.substring(0,json.length()-2)+"\n";
			
			json +="}";
		}
		
		
		if(json.length() > 1){
			json = json.substring(1);
		}
		
		return json;
	}
	
	public String listConstruct(String className, Return r, List<Model> objectList){
		String finalJson = "{\n\"busca\":{";

		finalJson += this.constructReturnMessages(r)+",\n";

		finalJson += "\n\"dados\":";
		if(objectList.size() > 0){

			finalJson += "["+this.listJSON(objectList)+"]\n";

		}else{
			finalJson += "[]\n";
		}

		return finalJson+"}\n}";
	}
	
	
	public String messageConstruct(Return r){
		String json = this.constructReturnMessages(r);
		json = json.substring(0, json.length()-1);
		return "{\n\t"+json+"\n}";
	}
	
	private String constructMessage(String messageName, List<String> errorList){
		String errors = "";
		for(String error : errorList){
			errors += ",\""+error+"\"";
		}
		
		if(errors.length() > 0){
			errors = errors.substring(1);
		}
		
		return "\""+ messageName +"\":["+errors+"]";
	}
	
	private String constructReturnMessages(Return r){
		String json = "";
		json += this.constructMessage("erro", r.getSimpleErrors())+",\n";
		json += this.constructMessage("msg", r.getMessage())+",\n";
		json += this.constructAttributeMessage("atb", r.getAttributeErrors())+"\n";
		return json;
	}
	
	
	private String constructAttributeMessage(String messageName, HashMap<String, String> errorList){
		String errors = "";
		String splitKey = "_";
		Iterator<String> it= errorList.keySet().iterator();
		
		while(it.hasNext()){
			String key = it.next();
			errors += ",{\"nomeClasse\":\""+key.split(splitKey)[0]+"\",\"nomeAttributo\":\""+key.split(splitKey)[1]+"\",\"msg\":\""+errorList.get(key)+"\"}";
		}
		
		if(errors.length() > 1)
			errors = errors.substring(1);
		
		return "\""+ messageName +"\":["+errors+"]";
	}
	
	

	
}
