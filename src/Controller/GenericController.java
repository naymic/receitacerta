package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Converter.GenericConverter;
import GenericDao.DAO;
import Interfaces.IApplicationSession;
import Interfaces.IController;
import JsonClasses.JReturn;
import Model.Model;
import Model.Usuario;
import Reflection.ReflectionDAO;
import Reflection.ReflectionDAORelation;


public class GenericController implements IController{

	ArrayList<String> validActions;
	HashMap<String, Object> variables;
	String jsonString; 
	IApplicationSession appSession;

	public GenericController(IApplicationSession appSession){
		this.initVariables();
		this.setAppSession(appSession);
	}
	
	public GenericController(){
		this.initVariables();
		
	}
	
	/**
	 * Iniciate class variables
	 */
	private void initVariables(){
		validActions = new ArrayList<>();
		variables = new HashMap<>();
	}
	
	/**
	 * Gets a map of all attributes of a object and its strings
	 * THIS METHOD IS NOT USER AT THE MOMENT
	 * @return
	 */
	public HashMap<String, Object> getVariableMapper() {
		return variables;
	}
	
	public void setJson(String json){
		this.jsonString=json;
	}
	
	public String getJson(){
		return this.jsonString;
	}

	
	@Override
	public JReturn validateAction(String action) {
		JReturn r = new JReturn();
		for(String s : getValidActionsList()){
			if(s.equalsIgnoreCase(action)){
				return r;
			}
			
		}
		
		r.addSimpleError("Action "+ action +" don't exist in the controller: "+ this.getClass().getName());
		return r;

	}


	@Override
	public void execute(JReturn r, String action) {
		//Checks if the given action is a valid action
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
	
	/**
	 * Initializes a object
	 * 
	 * @param Return	r
	 * @param boolean 	search 	Used to know if needs to check empty  
	 * @return
	 */
	public Model initObj(JReturn r){

		
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
	

	public boolean getUserSessionLoggedin(){
		
		if(this.getAppSession().getMapAttribute("loggedin") == null)
			return false;
		
		return (boolean)this.getAppSession().getMapAttribute("loggedin");
	}
	
	public  void setUserSessionLoggedin(boolean loggedin){
		this.getAppSession().setMapAttribute("loggedin", loggedin);
	}

}
