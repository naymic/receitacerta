package views;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import controllers.LoginController;
import dao.DAO;
import db.Config;
import interfaces.IApplicationSession;
import interfaces.IController;
import jrequestclasses.JRequest;
import jresponseclasses.JRedirect;
import jresponseclasses.JReturn;
import model.Usuario;
import reflection.GenericReflection;
import utils.StringUtils;
import utils.Transform;

/**
 * Servlet implementation class ViewController
 */
public class ViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	public void init() {
		
		
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHttp(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHttp(request, response);
	}
	
	/**
	 * Do the https request flow the response
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void doHttp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String content = "";
		HttpSession session = request.getSession(true);
		
		//Get real path
		Config.getInstance().setFilepath(request.getServletContext().getRealPath("/"));
		
		//Create a global return
		JReturn r = new JReturn();
		
		//Transform Json to JRequest
		Gson g = new Gson();
		JRequest[] jrequ = null;
		try{
			String json = request.getParameter("request");
			System.out.println("REQUEST:"+ json);
			jrequ = g.fromJson(json,  JRequest[].class);
		}catch(com.google.gson.JsonSyntaxException e){
			r.addSimpleError("JSON String is malformed, please check it!");	
			content = g.toJson(r);
			e.printStackTrace();
		}
		
		
		//Iterate throught all requests
		for(int i=0; r.isSuccess() && i<jrequ.length ; i++){
			content = process(jrequ[i], response, session, content, r);
		}
		
		response.setContentType("application/json");
		response.getWriter().println(content);
	}
	
	public String getAction(JRequest requ)throws Exception {
		if(requ.getAction() == null || requ.getAction().length() == 0){
			throw new Exception("Please set a action on the Interface");
		}
		
		return requ.getAction();
	}
	
	public String getUseCase(JRequest requ)throws Exception {
		if(requ.getUsecase() == null || requ.getUsecase().length() == 0){
			throw new Exception("Please set a usecase on the Interface");
		}
		
		return requ.getUsecase();
	}
	
	public String getClassname(JRequest requ)throws Exception {
		if(requ.getClassname() == null || requ.getClassname().length() == 0){
			throw new Exception("Please set a classname on the Interface");
		}
		
		return requ.getClassname();
	}
	
	public String process(JRequest requ, HttpServletResponse resp, HttpSession session, String content, JReturn r){
		String usecase = new String("");
		String action = new String("");
		String classname = new String("");
		String jString = new String("");
		
		//Set HTTP ViewSessionController
		ViewSessionController ics = new ViewSessionController();
		ics.setSession(session);
		
		
		//Set usecase and action
		try{
			usecase = this.getUseCase(requ);
			action = this.getAction(requ);
			classname = this.getClassname(requ);
 		}catch(Exception e){
			r.addSimpleError(e.getMessage());
			System.out.println(e.toString());
		}
		
		IController ic = null;
		//Get the controller for the required action
		if(r.isSuccess())
			ic = getController(r, usecase, ics);
		
		//Check if usecase needs authentication 
		//check if user is loggedin
		if(r.isSuccess())
			this.checkUserLogin(ic, r, ics, usecase, action, classname);
		
		//Generic use case execution
		if(r.isSuccess()){
			
			
			
			
			//Get all variables from the view and save it to the controller
			this.initVariables(requ, ic);
			
			//Execute the required action, the Return object is already transfered to the jsonMapper in the controller and is not user anymore
			ic.execute(r ,action);
			
			jString = ic.getJson();
			
			//Execution when no controller is found
		}else{
			jString = Transform.objectToJson(r);
		}
		
		
		System.out.println("RETURN: "+jString);
		return jString;
	}
	
	

	/**
	 * Get all variables from parameter an save it to the controller
	 * @param requ	Http request
	 * @param ic	Controlador
	 */
	protected void initVariables(JRequest requ, IController ic){
		String key = null;
		Iterator<String> i = requ.getData().keySet().iterator();
		ic.getObject().setClassName(requ.getClassname());
		while(i.hasNext()){
			key = i.next();
			ic.addVariable(key, requ.getData().get(key));
		}
		
	}
	

	/**
	 * Get a controller by its usecase
	 * @param r 		Return		Return to set messages if fails
	 * @param usecase	String		Name of the use case
	 * @return 			IController child of a IController
	 */
	public IController getController(JReturn r, String usecase, IApplicationSession ics){
		
		try{
			
			usecase = StringUtils.setFirstLetterUppercase(usecase);
			String controllerName = "controllers."+usecase+"Controller";

			//Check if class exist
			Class.forName(controllerName);
			
			
			IController ic = (IController) GenericReflection.instanciateObjectByName(controllerName);
			ic.setAppSession(ics);
			
			return ic;
		}catch(ClassNotFoundException e){
			r.addSimpleError("Don't exist a controller for the use case "+ usecase +"!");
			return null;
		}catch(StringIndexOutOfBoundsException obe){
			r.addSimpleError("Usecase is not set in the request! Please inform a usecase!");
			return null;
		}
		
		
		
	}
	
	/**
	 *  Set redirect to login user is not logged in && authentication is obligatory
	 * @param ic		IController				Use case controller
	 * @param r			Return					Return with messages for the view framework
	 * @param ics		ViewSessionController	Class with Global Session inside
	 * @param usecase	String					Use case to execute
	 */
	public void checkUserLogin(IController ic, JReturn r, ViewSessionController ics, String usecase, String action, String classname){
		
		//Add User status to Return			
		if(r.getUser() != null)
			r.getUser().setLoggedin(ic.isUserSessionLoggedin());
		
		//Check if use case needs authentication
		if(r.isSuccess() && ic.needAuthentication()){
			
					
			LoginController lc = (LoginController) getController(r, "Login", ics);

			//Check if user is already logged in
			if(!lc.isUserSessionLoggedin()){
				
				r.getRedirect().setRedirection("Login", "login", "login");
				r.setSuccess(false);
				
				//Set Redirection to 
				JRedirect successRedirect = new JRedirect();
				successRedirect.setRedirection(classname, usecase, action);
				lc.getAppSession().setMapAttribute("redirect", successRedirect);
			}else{
				r.setUser(ic.getUserSession());
				r.setSuccess(true);
			}
		}		
	}
	
	
	
	
	
	
}
