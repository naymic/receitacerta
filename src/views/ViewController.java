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
import interfaces.IApplicationSession;
import interfaces.IController;
import jrequestclasses.JRequest;
import jresponseclasses.JReturn;
import messageclient.MessageController;
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
		ViewSessionController vsc = new ViewSessionController();
		vsc.setSession(session);
		
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
			r.addSimpleError("3");	//JSON String is malformed, please check it!
			content = g.toJson(r);
			e.printStackTrace();
		}
		
		
		//Iterate throught all requests
		content = process(jrequ[0], r, vsc);
		
		
		
		try {
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			response.addHeader("Access-Control-Allow-Origin","*");
			response.addHeader("Access-Control-Allow-Methods","GET,POST");
			response.addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
			
			response.getWriter().println(content);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getAction(JRequest requ)throws Exception {
		if(requ.getAction() == null || requ.getAction().length() == 0){
			throw new Exception("4"); //Please set a action on the Interface
		}
		
		return requ.getAction();
	}
	
	public String getUseCase(JRequest requ)throws Exception {
		if(requ.getUsecase() == null || requ.getUsecase().length() == 0){
			throw new Exception("5"); //Please set a usecase on the Interface
		}
		
		return requ.getUsecase();
	}
	
	public String getClassname(JRequest requ)throws Exception {
		if(requ.getClassname() == null || requ.getClassname().length() == 0){
			throw new Exception("6"); //Please set a classname on the Interface
		}
		
		return requ.getClassname();
	}
	
	public String process(JRequest requ, JReturn response, IApplicationSession<?> session){
		String usecase = new String("");
		String action = new String("");
		String classname = new String("");
		String jString = new String("");
		MessageController mc = new MessageController();
		
		
		//Set usecase and action
		try{
			usecase = this.getUseCase(requ);
			action = this.getAction(requ);
			classname = this.getClassname(requ);
 		}catch(Exception e){
			response.addSimpleError(e.getMessage());
			//System.out.println(e.toString());
		}
		
		IController ic = null;
		//Get the controller for the required action
		if(response.isSuccess())
			ic = GenericController.getController(response, usecase, session);
		
		//Generic use case execution
		if(response.isSuccess()){
			
			//Get all variables from the view and save it to the controller
			this.initVariables(requ, ic);
			
			//Execute the required action, the Return object is already transfered to the jsonMapper in the controller and is not user anymore
			ic.execute(response ,action);
			
		}
		
		mc.getMessagesFromIds(response);
		jString = Transform.objectToJson(response);
		
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
