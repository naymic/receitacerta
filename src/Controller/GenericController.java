package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Converter.GenericConverter;
import Interfaces.IApplicationSession;
import Interfaces.IController;
import Model.Model;
import Model.Usuario;
import Reflection.ReflectionDAO;
import Reflection.ReflectionDAORelation;
import Utils.Return;

public class GenericController implements IController{

	ArrayList<String> validActions;
	HashMap<String, Object> variables;
	HashMap<String, String> jsonMapper; 
	IApplicationSession appSession;

	public GenericController(IApplicationSession appSession){
		validActions = new ArrayList<>();
		variables = new HashMap<>();
		jsonMapper= new HashMap<>();
		this.setAppSession(appSession);
	}
	
	public GenericController(){
		validActions = new ArrayList<>();
		variables = new HashMap<>();
		jsonMapper= new HashMap<>();
		
	}
	
	/**
	 * Gets a map of all attributes of a object and its strings
	 * THIS METHOD IS NOT USER AT THE MOMENT
	 * @return
	 */
	public HashMap<String, String> getJsonMapper() {
		return jsonMapper;
	}
	
	/**
	 * Gets a map of all attributes of a object and its strings
	 * THIS METHOD IS NOT USER AT THE MOMENT
	 * @return
	 */
	public HashMap<String, Object> getVariableMapper() {
		return variables;
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
		
		r.addSimpleError("Action "+ action +" don't exist in the controller: "+ this.getClass().getName());
		return r;

	}


	@Override
	public void execute(Return r, String action) {
		//Checks if the given action is a valid action
		r = this.validateAction(action);
		
		//Add the user 
		r.setUsuario(this.getUserSession());		
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
	
	
	public Model initObj(Return r){

		
		//Check if className is set
		if(!this.getVariableMapper().containsKey("className") && this.getClassName().length() > 0){
			r.addSimpleError("No given className, the controller have to reveice a className from the view");
			return null;
		}
		
		//Add Model. before the classname if not exist
		if(!this.getVariableMapper().get("className").toString().contains("Model.")){
			this.getVariableMapper().put("className", "Model."+this.getVariableMapper().get("className"));
		}
		
	
		Iterator<String> it = this.getVariableKeys();
		String paramName;
		String className = (String) this.getVariableValue("className");
		Model obj = ReflectionDAO.instanciateObjectByName(className);	
		ReflectionDAORelation rdr = new ReflectionDAORelation(obj);


		while(it.hasNext()){
			paramName = it.next();
			
			//Get just variable for the object
			if(paramName.contains("campo.") ){
				String attributeName = paramName.split("\\.")[1];
				Object value = null;
				try{
					//Convert the String value from the view to the Model class
					value = GenericConverter.convert(rdr.getMethodValueClass(rdr.getGetMethodByColumname(attributeName)), this.getVariableValue(paramName));
					
					if(rdr.isRequired(rdr.getGetMethodByColumname(attributeName)) && value.toString().length() == 0)
						r.addAttributeError(obj.getClass().getName(), attributeName, "Field  is empty but required: "+ attributeName +" for "+ rdr.getObject().getClass().getSimpleName());
					
				}catch(Exception e){
					System.out.println(e.getMessage());
					r.addAttributeError(obj.getClass().getName(), attributeName, "Field has wrong caracters or is empty: "+ attributeName +" for "+ rdr.getObject().getClass().getSimpleName());
				}
				//Set just if value is set
				if(this.getVariableValue(paramName).toString().length() > 0){
					rdr.setValueFromAttributename(attributeName, value);
				}
			}

		}
		return obj;
	}
	
	/**
	 * Gets the className of the mapper
	 * @return
	 */
	public String getClassName() {
		return (String) this.getVariableValue("className");
	}
	
	/**
	 * Get the application session
	 * @return
	 */
	public IApplicationSession getAppSession() {
		return appSession;
	}

	/**
	 * Sets the application session
	 * @param appSession
	 */
	public void setAppSession(IApplicationSession appSession) {
		this.appSession = appSession;
	}
	
	
	public void setRedirect(String redirect){
		this.getAppSession().setMapAttribute("redirect", redirect);
	}
	
	public String getRedirect(){
		return (String) this.getAppSession().getMapAttribute("redirect");
	}
	
	
	public void setUserSession( Usuario u){
		this.getAppSession().setMapAttribute("user", u);
	}
	
	public Usuario getUserSession(){
		if(this.getAppSession().getMapAttribute("user") == null)
			return null;
		
		return (Usuario) this.getAppSession().getMapAttribute("user");
	}
	

}
