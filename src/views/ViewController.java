package views;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import controllers.GenericController;
import db.Config;
import interfaces.IController;
import jrequestclasses.JRequest;
import jresponseclasses.JReturn;
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
	public void doHttp(HttpServletRequest request, HttpServletResponse response){
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
			//System.out.println("REQUEST:"+json);
			jrequ = g.fromJson(json,  JRequest[].class);
		}catch(com.google.gson.JsonSyntaxException e){
			r.addSimpleError("JSON String is malformed, please check it!");	
			content = g.toJson(r);
			e.printStackTrace();
		}
		
		
		//Iterate throught all requests
		content = process(jrequ[0], r, session);
		
		response.setContentType("application/json");
		try {
			response.getWriter().println(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public String process(JRequest requ, JReturn r, HttpSession session){
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
			//System.out.println(e.toString());
		}
		
		IController ic = null;
		//Get the controller for the required action
		if(r.isSuccess())
			ic = GenericController.getController(r, usecase, ics);
		
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
		
		
		//System.out.println("RETURN: "+jString);
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
		
		//Set page number
		try{
			ic.setPageNumber(requ.getPagemanager().getActualPage());
			
		}catch(NullPointerException npe){
			ic.setPageNumber(null);
		}
		
	}
	
	
	
	
	
	
}
