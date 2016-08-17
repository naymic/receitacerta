package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Converter.GenericConverter;
import GenericDao.DAO;
import Interfaces.IApplicationSession;
import Interfaces.IController;
import Interfaces.IUser;
import JsonClasses.JObject;
import JsonClasses.JReturn;
import Model.Model;
import Model.Usuario;
import Reflection.ReflectionDAO;
import Reflection.ReflectionDAORelation;


public class GenericController implements IController{
	
	String usecase;
	String action;
	ArrayList<String> validActions;
	JObject jobject;
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
		jobject = new JObject();
	}
	
	/**
	 * Gets a map of all attributes of a object and its strings
	 * THIS METHOD IS NOT USER AT THE MOMENT
	 * @return
	 */
	public JObject getObject() {
		return jobject;
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
		this.getObject().addAttribute(key, value);
	}
	
	public Object getVariableValue(String key){
		try{
			if(this.getObject().existAttribute(key))
				return this.getObject().getAttribute(key);
			else
				throw new Exception("Key: "+key+" dont exit in the variable map");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public Iterator<String> getVariableKeys(){
		return this.getObject().getAttributeKeys();
	}
	
	public Iterator<Object> getVariableValues(){
		return this.getObject().getAttributeValues();
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
		if(this.getObject().getClassName() == null || this.getObject().getClassName().length() == 0){
			r.addSimpleError("No given className, the controller have to reveice a className from the view");
			return null;
		}
		
		//Add Model. before the classname if not exist
		if(!this.getObject().getClassName().toString().contains("Model.")){
			this.getObject().setClassName("Model."+this.getObject().getClassName());
		}
		
	
		Iterator<String> it = this.getVariableKeys();
		String paramName;
		String className = (String) this.getObject().getClassName();
		Model obj = ReflectionDAO.instanciateObjectByName(className);	
		ReflectionDAORelation rdr = new ReflectionDAORelation(obj);


		while(it.hasNext()){
			paramName = it.next();
			
			//Get just variable for the object
			String attributeName = paramName;
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
		
		return obj;
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
	
	public IUser getUserSession(){
		
		if(this.getAppSession().getMapAttribute("user") == null)
			return null;
		
		return (IUser)this.getAppSession().getMapAttribute("user");
	}
	
	public  void setUserSession(IUser user){
		this.getAppSession().setMapAttribute("user", user);
	}
	

	public boolean isUserSessionLoggedin(){
		
		if(this.getAppSession().getMapAttribute("user") == null)
			return false;
		
		IUser iu= (IUser)this.getAppSession().getMapAttribute("user");
		return iu.isLoggedin();
	}


}
