package Controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import Interfaces.IController;
import Model.Model;
import Reflection.ReflectionDAO;
import Reflection.ReflectionDAORelation;
import Utils.Return;

public class GenericController implements IController{

	ArrayList<String> validActions;
	HashMap<String, Object> variables;
	HashMap<String, String> jsonMapper; 
	
	
	public GenericController(){
		validActions = new ArrayList<>();
		variables = new HashMap<>();
		jsonMapper= new HashMap<>();
		
	}
	/**
	 * Gets a map of all attributes of a object and its strings
	 * @return
	 */
	public HashMap<String, String> getJsonMapper() {
		return jsonMapper;
	}
	
	public void setUniqueJson(String json){
		this.jsonMapper.put("json", json);
	}
	
	public String getUniqueJson(){
		return this.jsonMapper.get("json");
	}

	
	@Override
	public Return validateAction(String action) {
		Return r = new Return();
		for(String s : getValidActionsList()){
			if(s.equalsIgnoreCase(action)){
				return r;
			}
			
		}
		
		r.addSimpleError("Action dont exist in: "+ this.getClass() +" action:"+ action);
		return r;

	}

	@Override
	public void execute(Return r, String action) {
		r = this.validateAction(action);
	}

	@Override
	public List<String> getValidActionsList() {
		validActions = new ArrayList<String>();
		validActions.add("index");
		
		return validActions;
	}


	@Override
	public boolean needAuthentication() {
		return false;
	}

	@Override
	public boolean dontShowCommonPage() {
		return false;
	}
	
	
	public void addVariable(String key, Object value){
		this.variables.put(key, value);
	}
	
	public Object getVariableValue(String key){
		try{
			if(this.variables.containsKey(key))
				return this.variables.get(key);
			else
				throw new Exception("Key: "+key+" dont exit in the variable map");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public Iterator<String> getVariableKeys(){
		return this.variables.keySet().iterator();
	}
	
	public Iterator<Object> getVariableValues(){
		return this.variables.values().iterator();
	}
	

}
