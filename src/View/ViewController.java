package View;

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

import Controller.LoginController;
import GenericDao.DAO;
import Interfaces.IApplicationSession;
import Interfaces.IController;
import JsonClasses.JRedirect;
import JsonClasses.JRequest;
import JsonClasses.JReturn;
import Model.Usuario;
import Reflection.GenericReflection;
import Utils.Config;
import Utils.Transform;

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
		// TODO Auto-generated method stub
		
	
		
		//response.getWriter().println("");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String content = "";
		HttpSession session = request.getSession(true);
		
		//Get real path
		Config.getInstance().setFilepath(request.getServletContext().getRealPath("/"));
		
		//Create a global return
		JReturn r = new JReturn();
		
		//Transform Json to JRequest
		Gson g = new Gson();
		JRequest[] jrequ;
		String json = request.getParameter("request");
		jrequ = g.fromJson(json,  JRequest[].class);
		
		//Iterate throught all requests
		for(int i=0; i<jrequ.length; i++){
			content = process(jrequ[i], response, session, content, r);
		}
		response.getWriter().println(content);
	}
	
	public String getAction(JRequest requ)throws Exception {
		if(requ.getAction() == null || requ.getAction().length() == 0){
			throw new Exception("Please set a action in on the Interface");
		}
		
		return requ.getAction();
	}
	
	public String getUseCase(JRequest requ)throws Exception {
		if(requ.getUsecase() == null || requ.getUsecase().length() == 0){
			throw new Exception("Please set a use case in on the Interface");
		}
		
		return requ.getUsecase();
	}
	
	public String getClassname(JRequest requ)throws Exception {
		if(requ.getClassname() == null || requ.getClassname().length() == 0){
			throw new Exception("Please set a classname in on the Interface");
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
		
		
		//Get the controller for the required action
		IController ic = getController(r, usecase, ics);
		
		//Check if usecase needs authentication 
		//check if user is loggedin
		this.checkUserLogin(ic, r, ics, usecase, action, classname);
		
		//Generic use case execution
		if(r.isSuccess()){
			//Get all variables from the view and save it to the controller
			this.initVariables(requ, ic);
			System.out.println(ic.getJson().length());
			
			//Execute the required action, the Return object is already transfered to the jsonMapper in the controller and is not user anymore
			ic.execute(r ,action);
			
			jString = ic.getJson();
			
			//Execution when no controller is found
		}else{
			jString = Transform.objectToJson(r);
		}
		
		
		System.out.println(jString);
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
		String controllerName = "Controller."+usecase+"Controller";

		try{
			Class.forName(controllerName);
		}catch(ClassNotFoundException e){
			r.addSimpleError("Don't exist a controller for the use case "+ usecase +"!");
			return null;
		}
		
		IController ic = (IController) GenericReflection.instanciateObjectByName(controllerName);
		ic.setAppSession(ics);
		return ic;
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
		r.getUser().setLoggedin(ic.isUserSessionLoggedin());
		
		//Check if use case needs authentication
		if(r.isSuccess() && ic.needAuthentication()){
			LoginController lc = (LoginController) getController(r, "Login", ics);

			//Check if user is already logged in
			if(!lc.isUserSessionLoggedin()){
				r.getRedirect().setRedirection("Login", "login", "login");
				r.setSuccess(false);
				
				//Set Redirection to 
				lc.getAppSession().setMapAttribute("redirect", r.getRedirect());
			}else{
				r.setUser(ic.getUserSession());
				r.setSuccess(true);
			}
		}		
	}
	
	
	
	
	
	
}
