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
import jresponseclasses.JReturn;
import model.Model;
import model.Usuario;
import reflection.ReflectionController;
import reflection.ReflectionDAO;
import reflection.ReflectionDAORelation;
import utils.RequestObject;
import utils.Transform;
import annotations.AControllerMethod;
import annotations.AModelClasses;

public class GenericController implements IController{
	
	String usecase;
	String action;
	ArrayList<String> validActions;
	RequestObject jobject;
	String jsonString; 
	IApplicationSession appSession;
	Model modelObject;
	Integer pageNumber; //The actual number of page to visualize on the view
	

	public GenericController(IApplicationSession appSession){
		this.setAppSession(appSession);
		this.initVariables();
	}
	
	public GenericController(){
		this.initVariables();
		
	}
	
	/**
	 * Iniciate class variables
	 */
	private void initVariables(){
		validActions = new ArrayList<>();
		jobject = new RequestObject();		
		
	}
	
	/**
	 * Gets a map of all attributes of a object and its strings
	 * THIS METHOD IS NOT USER AT THE MOMENT
	 * @return
	 */
	public RequestObject getObject() {
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
		
		
		//Find out if action need to check attributes first
		AControllerMethod acm = null;
		Boolean	check = true;
		try{
			acm = methodAction.getAnnotation(AControllerMethod.class);
			check = acm.checkAttributes();
		}catch(NullPointerException e){	check = true;}
		
		
		
		//Iniciate the object from the data given by the view
		if(r.isSuccess())
			this.setModelObject(this.initObj(r, check));
			
		
		//Check if PK is set or not
		if(acm != null && acm.checkPK() && !check)	
			checkPK(r, this.getModelObject());
		
		
		//Verify attributes 
		if(r.isSuccess() && check)
			this.getModelObject().verifyGeneric(r);
		
		
		
		//Set User to the object if required
		try{			
			AModelClasses amc = this.getModelObject().getClass().getAnnotation(AModelClasses.class);
			if(amc.needUserObject() && this.needAuthentication() && this.isUserSessionLoggedin())
				this.setUserObject();
		}catch(NullPointerException npe){
			System.out.println("Please add a AModelClasses annotation to the model: "+ this.getModelObject().getClass().getName());
		}
		
		
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
	
	
	public Model initObj(JReturn r){
		return initObj(r, true);
	}
	
	/**
	 * Initializes a object
	 * With Fieldnames
	 * 
	 * @param Return	r
	 * @param boolean 	search 	Used to know if needs to check empty  
	 * @return
	 */
	public Model initObj(JReturn r, boolean checkAttributes){


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
		
		//Invoke object
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

			//Convert the String value from the view to the Model class
			Method m = null;
			
			//Get and check the given Method to set and get values from attributes
			m = this.getAndCheckMethod(r, rdr, MType.get, fieldName);
			
			//Check and convert input values
			boolean convertError = false;
			try{
				value = GenericConverter.convert(rdr.getMethodValueClass(m), this.getVariableValue(paramName));
			}catch(Exception e){
				System.out.println(e.getMessage());
				r.addAttributeError(obj.getClass().getName(), fieldName, "Field has wrong caracters or is empty: "+ fieldName +" for "+ rdr.getObject().getClass().getSimpleName());
				convertError = true;
			}

			//Check for empty fields but just if dont exist convert error allready
			if(!convertError && rdr.isRequired(m) && (value == null || value.toString().length() == 0) && checkAttributes)
				r.addAttributeError(obj.getClass().getName(), fieldName, "Field  is empty but required: "+ fieldName +" for "+ className);
			



			//Set just if value is set
			if(this.getVariableValue(paramName) != null && this.getVariableValue(paramName).toString().length() > 0){
				m = this.getAndCheckMethod(r, rdr, MType.set, fieldName, value.getClass());
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
	
	public Method getAndCheckMethod(JReturn r, ReflectionDAORelation rdr, MType type, String fieldName, Class<?>... args){
		Method m1 = null;
		
		try{
			m1= rdr.getAllMethods(fieldName, type, args);
		}catch(RuntimeException re){
			r.addAttributeError(rdr.getObjectClass().getSimpleName(), fieldName, "No such attribute! Class: " +rdr.getObjectClass().getSimpleName()+" Attribute: " +fieldName);
			re.printStackTrace();
		}
		
		return m1;
	}

	/**
	 * 
	 * @param obj
	 * @param rdr 
	 */
	private void setUserObject() {
		ReflectionDAORelation rdr = new ReflectionDAORelation(this.getModelObject());
		ArrayList<Method> mList = rdr.getGetMethods();
		
		for(Method method : mList){
			try{
				AModelClasses amc = rdr.getMethodValueClass(method).getAnnotation(AModelClasses.class);
				if(amc.isUserModel()){
					rdr.setMethodValue(method, (Usuario)this.getUserSession());
					break;
				}
			}catch(Exception e){
				
			}
			
		}
		
	}
	
	
	/**
	 * Check if PK of given object is set or not
	 * @param r
	 * @param object
	 */
	private ReflectionDAORelation checkPK(JReturn r, Model object) {
		ReflectionDAORelation rdr = new ReflectionDAORelation(object);
		if(rdr.getPK() == null){
			r.addSimpleError("Primary key is not set. Object "+ object.getClass().getSimpleName() +" not found!");
		}
		
		return rdr;
	}

	/**
	 * Get page number and pass it to DAO to 
	 * limit the min and max selected rows
	 * @return
	 */
	public Integer getPageNumber() {
		return pageNumber;
	}

	/**
	 * Set page number and pass it to DAO to 
	 * limit the min and max selected rows
	 * @return
	 */
	public void setPageNumber(Integer object) {
		this.pageNumber = object;
	}

}
