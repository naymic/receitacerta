package controllers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import converters.GenericConverter;
import dao.DAO;
import enums.MType;
import exceptions.NoActionException;
import interfaces.IApplicationSession;
import interfaces.IController;
import interfaces.IUser;
import jsonclasses.JObject;
import jsonclasses.JReturn;
import model.Model;
import model.Usuario;
import reflection.ReflectionController;
import reflection.ReflectionDAO;
import reflection.ReflectionDAORelation;
import utils.Transform;


public class GenericController implements IController{
	
	String usecase;
	String action;
	ArrayList<String> validActions;
	JObject jobject;
	String jsonString; 
	IApplicationSession appSession;
	Model modelObject;

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
	public Method validateAction(ReflectionController rController, String action)throws NoActionException {
		boolean test = false;
		Method method = null;
		
		return rController.getAction(this.getUsecaseName(), action);
	}


	@Override
	public void execute(JReturn r, String action) {
		ReflectionController rController = new ReflectionController(this);
		Method methodAction = null;
		
		//Checks if the given action is a valid action
		try {
			methodAction = this.validateAction(rController, action);
		} catch (NoActionException e) {
			r.addSimpleError(e.getMessage());
		}
		
		//if action exist in child class execute the action
		if(r.isSuccess())
			this.setModelObject(this.initObj(r));
			
		
			//Execute action
		if(r.isSuccess()){
			try {
				rController.executeAction(this, methodAction, r, this.getModelObject());
			} catch (IllegalAccessException e) {
				r.addSimpleError("Access to action is not allowed! Action: "+ action);
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				r.addSimpleError("Passed parameters for action are wrong! Action: "+ action);
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				r.addSimpleError("Action could not be executed (invoked)! Action: "+ action);
				e.printStackTrace();
			}
		}
			
			
		
		//Transform the return to json
		this.setJson(Transform.objectToJson(r));
		
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
	 * With Fieldnames
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
		
		//Add model. before the classname if not exist
		if(!this.getObject().getClassName().toString().contains("model.")){
			this.getObject().setClassName("model."+this.getObject().getClassName());
		}
		
	
		
		String paramName;
		String className = (String) this.getObject().getClassName();
		Model obj = null;
		try{
			obj = ReflectionDAO.instanciateObjectByName(className);	
		}catch(RuntimeException re){
			r.addSimpleError(re.getMessage());
			return null;
		}
		
		ReflectionDAORelation rdr = new ReflectionDAORelation(obj);
		Iterator<String> it = this.getVariableKeys();

		while(it.hasNext()){
			paramName = it.next();
			
			//Get just variable for the object
			String fieldName = paramName;
			Object value = null;
			try{
				//Convert the String value from the view to the Model class
				Method m= rdr.getMethodByFieldname(fieldName, MType.get);
				value = GenericConverter.convert(rdr.getMethodValueClass(m), this.getVariableValue(paramName));
				
				if(rdr.isRequired(rdr.getMethodByFieldname(fieldName, MType.get)) && value.toString().length() == 0)
					r.addAttributeError(obj.getClass().getName(), fieldName, "Field  is empty but required: "+ fieldName +" for "+ rdr.getObject().getClass().getSimpleName());
				
			}catch(Exception e){
				System.out.println(e.getMessage());
				r.addAttributeError(obj.getClass().getName(), fieldName, "Field has wrong caracters or is empty: "+ fieldName +" for "+ rdr.getObject().getClass().getSimpleName());
			}
			//Set just if value is set
			if(this.getVariableValue(paramName) != null && this.getVariableValue(paramName).toString().length() > 0){
				Method m = rdr.getMethodByFieldname(fieldName, MType.set, value.getClass());
				rdr.setMethodValue(m, value);
				//rdr.setFieldValue(fieldName, value);
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
	
	public void setModelObject(Model modelObject){
		this.modelObject = modelObject;
	}
	
	public Model getModelObject(){
		return modelObject;
	}
	
	public String getUsecaseName(){
		String usecase = this.getClass().getSimpleName();
		int i = usecase.indexOf("Controller");
		return usecase.substring(0, i);
	}


}
