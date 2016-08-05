package View;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controller.LoginController;
import GenericDao.DAO;
import Interfaces.IApplicationSession;
import Interfaces.IController;
import JsonClasses.JRedirect;
import JsonClasses.JReturn;
import Model.Usuario;
import Reflection.GenericReflection;
import Utils.Config;
import Utils.JSON;

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
		
		Config.getInstance().setFilepath(request.getServletContext().getRealPath("/"));
		
		content = process(request, response, session, content);
		response.getWriter().println(content);
	}
	
	public String getAction(HttpServletRequest requ)throws Exception {
		if(requ.getParameter("action") == null || requ.getParameter("action").length() == 0){
			throw new Exception("Please set a action in on the Interface");
		}
		
		return requ.getParameter("action");
	}
	
	public String getUseCase(HttpServletRequest requ)throws Exception {
		if(requ.getParameter("usecase") == null || requ.getParameter("usecase").length() == 0){
			throw new Exception("Please set a use case in on the Interface");
		}
		
		return requ.getParameter("usecase");
	}
	
	public String getClassname(HttpServletRequest requ)throws Exception {
		if(requ.getParameter("className") == null || requ.getParameter("className").length() == 0){
			throw new Exception("Please set a classname in on the Interface");
		}
		
		return requ.getParameter("className");
	}
	
	public String process(HttpServletRequest requ, HttpServletResponse resp, HttpSession session, String content){
		JReturn r = new JReturn();
		JSON j = new JSON();
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
			jString = j.returnConstruct(r);
		}
		
		
		System.out.println(jString);
		return jString;
	}
	
	

	/**
	 * Get all variables from parameter an save it to the controller
	 * @param requ	Http request
	 * @param ic	Controlador
	 */
	protected void initVariables(HttpServletRequest requ, IController ic){
		String key = null;
		Enumeration<String> e = requ.getParameterNames();
		while(e.hasMoreElements()){
			key = e.nextElement();
			ic.addVariable(key, requ.getParameter(key));
		}
		
	}
	

	/**
	 * Get a controller by its usecase
	 * @param r 		Return		Return to set messages if fails
	 * @param usecase	String		Name of the use case
	 * @return 			IController child of a IController
	 */
	public IController getController(JReturn r, String usecase, IApplicationSession ics){
		String className = "Controller."+usecase+"Controller";

		try{
			Class.forName(className);
		}catch(ClassNotFoundException e){
			r.addSimpleError("Don't exist a controller for the use case "+ usecase +"!");
			return null;
		}
		
		IController ic = (IController) GenericReflection.instanciateObjectByName(className);
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
		r.getUser().setLoggedin(ic.getUserSessionLoggedin());
		
		//Check if use case needs authentication
		if(r.isSuccess() && ic.needAuthentication()){
			LoginController lc = (LoginController) getController(r, "Login", ics);

			//Check if user is already logged in
			if(!lc.getUserSessionLoggedin()){
				JRedirect redrct = new JRedirect();
				redrct.setRedirection("Login", "login", "login");
				
				r.setSuccess(false);
				lc.getAppSession().setMapAttribute("redirectUseCase", usecase);
				lc.getAppSession().setMapAttribute("redirectAction", action);
				lc.getAppSession().setMapAttribute("redirectClassname", classname);
			}else{
				r.getUser().setLoggedin(true);
				r.setSuccess(true);
			}
		}		
	}
	
	
	
	
	
	
}
