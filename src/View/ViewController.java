package View;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.*;
import Interfaces.IController;
import Model.Model;
import Reflection.GenericReflection;
import Reflection.ReflectionDAO;
import Reflection.ReflectionDAORelation;
import Utils.Config;
import Utils.JSON;
import Utils.Return;

/**
 * Servlet implementation class ViewController
 */
public class ViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	
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
		
		Config.getInstance().setFilepath(request.getServletContext().getRealPath("/"));

		
		response.getWriter().println("");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String content = "";

		content = process(request, response, content);
		
		
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
	
	protected String process(HttpServletRequest requ, HttpServletResponse resp, String content){
		Return r = new Return();
		String usecase = "";
		String action = "";
		
		try{
			usecase = this.getUseCase(requ);
			action = this.getAction(requ);
		}catch(Exception e){
			r.addSimpleError(e.getMessage());
			System.out.println(e.toString());
		}
		
		String jString = "";
		
		//Get the controller for the required action
		IController ic = getController(r, usecase);
		
		if(r.isSuccess()){
			//Get all variables from the view and save it to the controller
			this.initVariables(requ, ic);
			System.out.println(ic.getJsonMapper().size());
			
			//Execute the required action, the Return object is already transfered to the jsonMapper in the controller and is not user anymore
			ic.execute(r ,action);
			
			jString = ic.getUniqueJson();
			
			
		}else{
			
			JSON j = new JSON();
			r.addSimpleError("No controller found for this action: "+ action);
			jString = j.messageConstruct(r);
			
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
	public IController getController(Return r, String usecase){
		String className = "Controller."+usecase+"Controller";

		try{
			Class.forName(className);
		}catch(ClassNotFoundException e){
			r.addSimpleError("Don't exist a controller for the use case "+ usecase +"!");
			return null;
		}
		
		return (IController) GenericReflection.instanciateObjectByName(className);
	}
	
	
}
