package controllers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import converters.GenericConverter;
import dao.DAO;
import db.Config;
import enums.MType;
import exceptions.NoActionException;
import interfaces.IApplicationSession;
import interfaces.IController;
import interfaces.IUser;
import jresponseclasses.JData;
import jresponseclasses.JRedirect;
import jresponseclasses.JReturn;
import model.Model;
import model.User;
import model.Usuario;
import reflection.GenericReflection;
import reflection.ReflectionController;
import reflection.ReflectionDAO;
import reflection.ReflectionDAORelation;
import reflection.ReflectionModel;
import utils.RequestObject;
import utils.StringUtils;
import utils.Transform;
import views.ViewSessionController;
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
		
		return rController.getAction(action);
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
		
		AControllerMethod acm = null;
		if(r.isSuccess()){
			//Find out if action need to check attributes first
			
			try{
				acm = ReflectionController.getAControllerMethod(methodAction);			
			}catch(RuntimeException e){	
				r.addSimpleError("13");//Please add the AControllerMethod annotation in class: "+this.getClass().getSimpleName()+" and action: "+action
			}

			if(!Config.getInstance().isTestDB())
				this.checkUserLogin(r, acm, action, action);


			//Iniciate the object from the data given by the view
			if(r.isSuccess()){
				this.setModelObject(this.initObj(r, acm.checkAttributes()));
			}

			//Check if PK is set or not
			if(r.isSuccess() && acm != null && acm.checkPK() && !acm.checkAttributes())	
				checkPK(r, this.getModelObject());


			//Verify attributes 
			if(r.isSuccess() && acm.checkAttributes()){
				this.getModelObject().verifyGeneric(r);

			}

		}
		
		//Set User to the object if required
		if(r.isSuccess()&& this.getModelObject() != null){
			try{			
				
				AModelClasses amc = ReflectionModel.getAnnotationFromModel(this.getModelObject().getClass());
				if(this.needUserObject(acm, amc))
					this.setUserObject();
			}catch(RuntimeException re){
				String errormsg = "14"; //"Please add a AModelClasses annotation to the model: "+ this.getModelObject().getClass().getName()
				System.out.println(errormsg);
				r.addSimpleError(errormsg);
			}
		}
		
	
			
		
		
		//Execute action
		if(r.isSuccess()){
			try {
				rController.executeAction(this, methodAction, r, this.getModelObject());
			} catch (IllegalAccessException e) {
				r.addSimpleError("15");//"Access to action is not allowed! Action: "+ action
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				r.addSimpleError("16");//Passed parameters for action are wrong! Action: "+ action
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				r.addSimpleError("17"); //Action could not be executed (invoked)! Action: "+ action
				e.printStackTrace();
			}
		}
			
			
		
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
				throw new Exception("18"); //Key: "+key+" dont exit in the variable map
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
			r.addSimpleError("6");//No given className, the controller have to reveice a className from the view
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

			
			if(paramName.contains(".")){
				String[] subParams = paramName.split(".");
				paramName = subParams[0];
				
				for(int i = 1; i < subParams.length; i++){
					
				}
				
			}
			
			initAttribute(r, checkAttributes, paramName, className, obj, rdr);


		}

		return obj;
	}

	/**
	 * Initializes one attribute
	 * @param r
	 * @param checkAttributes
	 * @param paramName
	 * @param className
	 * @param obj
	 * @param rdr
	 */
	private void initAttribute(JReturn r, boolean checkAttributes, String paramName, String className, Model obj,
			ReflectionDAORelation rdr) {
		//Get just variable for the object
		String fieldName = paramName;
		Object value = null;

		//Convert the String value from the view to the Model class
		Method m = null;
		
		//Get and check the given Method to set and get values from attributes
		m = this.getAndCheckMethod(r, rdr, MType.get, fieldName);
		if(m == null) return; //Stop any further execution

		//Check and convert input values
		boolean convertError = false;
		try{
			Object viewValue = this.getVariableValue(paramName);
			if(!(viewValue == null) && viewValue.toString().length() > 0 )
				value = GenericConverter.convert(rdr.getMethodValueClass(m), viewValue);
		}catch(Exception e){
			System.out.println(e.getMessage());
			r.addAttributeError(obj.getClass().getName(), fieldName,"19" );//"Field has wrong caracters or is empty: "+ fieldName +" for "+ rdr.getObject().getClass().getSimpleName()
			convertError = true;
		}

		//Check for empty fields but just if dont exist convert error allready
		if(!convertError && rdr.isRequired(m) && checkAttributes && (value == null || value.toString().length() == 0) )
			r.addAttributeError(obj.getClass().getName(), fieldName, "20"); //"Field  is empty but required: "+ fieldName +" for "+ className
		



		//Set just if value is set
		if(this.getVariableValue(paramName) != null && this.getVariableValue(paramName).toString().length() > 0){
			m = this.getAndCheckMethod(r, rdr, MType.set, fieldName, value.getClass());
			if(m == null) return; //Stop any further execution
			rdr.setMethodValue(m, value);
			//rdr.setFieldValue(fieldName, value);
		}
	}
	
	
	/**
	 * Prepare the data for a form
	 * @param list
	 * @return
	 */
	protected ArrayList<JData> prepareFormData(ArrayList<ArrayList<Model>> list){
		ArrayList<JData> jdataList = new ArrayList<>();
		Iterator<ArrayList<Model>> itList = list.iterator();
		
		while(itList.hasNext()){
			ArrayList<Model> modelList = itList.next();
			JData jd= new JData();
			jd.setDataList(modelList);
			jdataList.add(jd);
		}
		
		
		return jdataList;
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
	
	
	public void setRedirect(JRedirect redirect){
		this.getAppSession().setMapAttribute("redirect", redirect);
	}
	
	public JRedirect getRedirect(){
		return (JRedirect) this.getAppSession().getMapAttribute("redirect");
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
		
		if(this.getAppSession() == null)
			return false;
		
		if((!this.getAppSession().existMapAttribute("user")) || (this.getAppSession().getMapAttribute("user") == null))
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
			r.addAttributeError(rdr.getObjectClass().getSimpleName(), fieldName, "21");//"No such attribute! Class: " +rdr.getObjectClass().getSimpleName()+" Attribute: " +fieldName
			re.printStackTrace();
		}
		
		return m1;
	}

	
	
	private boolean needUserObject(AControllerMethod acm, AModelClasses amc){
		
		if(this.getAppSession() != null && this.isUserSessionLoggedin() && acm.needAuthentication() && amc.needUserObject() && !Config.getInstance().isTestDB())
			return true;
		
		return false;
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
				AModelClasses amc = ReflectionModel.getAnnotationFromModel(rdr.getMethodValueClass(method));
				if(amc.isUserModel()){
					rdr.setMethodValue(method, (IUser)this.getUserSession());
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
			r.addSimpleError("22");//Primary key is not set. Object "+ object.getClass().getSimpleName() +" not found!
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
	
	/**
	 * Get a controller by its usecase
	 * @param r 		Return		Return to set messages if fails
	 * @param usecase	String		Name of the use case
	 * @return 			IController child of a IController
	 */
	public static IController getController(JReturn r, String usecase, IApplicationSession ics){
		
		try{
			
			usecase = StringUtils.setFirstLetterUppercase(usecase);
			String controllerName = "controllers."+usecase+"Controller";

			//Check if class exist
			Class.forName(controllerName);
			
			
			IController ic = (IController) GenericReflection.instanciateObjectByName(controllerName);
			ic.setAppSession(ics);
			
			return ic;
		}catch(ClassNotFoundException e){
			r.addSimpleError("23");//Don't exist a controller for the use case "+ usecase +"!
			return null;
		}catch(StringIndexOutOfBoundsException obe){
			r.addSimpleError("5");//Usecase is not set in the request! Please inform a usecase!
			return null;
		}
		
		
		
	}
	
	
	/**
	 *  Set redirect to login user is not logged in && authentication is obligatory
	 * @param controller		IController				Use case controller
	 * @param r			Return					Return with messages for the view framework
	 * @param ics		ViewSessionController	Class with Global Session inside
	 * @param usecase	String					Use case to execute
	 */
	public void checkUserLogin(JReturn r, AControllerMethod amc, String usecase, String action){

		//Add User status to Return			
		if(r.getUser() != null)
			r.getUser().setLoggedin(this.isUserSessionLoggedin());
		else
			r.getUser().setLoggedin(false);

		//Check if use case needs authentication
		if(r.isSuccess() && amc.needAuthentication()){


			if(this.getAppSession() == null)
				this.setAppSession(new ViewSessionController());
			
			
			LoginController lc = (LoginController) GenericController.getController(r, "Login", this.getAppSession());

			//Check if user is already logged in
			if(!lc.isUserSessionLoggedin()){

				r.getRedirect().setRedirection("Usuario", "login", "login");
				r.setSuccess(false);

				//Set Redirection to 
				JRedirect successRedirect = new JRedirect();
				successRedirect.setRedirection(this.getClass().getSimpleName(), usecase, action);
				lc.getAppSession().setMapAttribute("redirect", successRedirect);
			}else{
				r.setUser(this.getUserSession());
				r.setSuccess(true);
			}
		}		
	}

}
