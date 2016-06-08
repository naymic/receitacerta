package View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.CRUDController;
import Controller.GenericController;
import Interfaces.IController;
import Model.Model;
import Reflection.ReflectionDAO;
import Reflection.ReflectionDAORelation;
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
		
		response.getWriter().println("");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String content = "";

		process(request, response, content);
		
		
		response.getWriter().println(content);
	}
	
	public String getAction(HttpServletRequest requ) {
		return requ.getParameter("action");
	}
	
	protected String process(HttpServletRequest requ, HttpServletResponse resp, String content){
		Return r = new Return();
		String action = this.getAction(requ);
		String jString = "";
		
		//Get the controller for the required action
		IController ic = getController(r, action);
		
		
		if(r.isSuccess()){
			//Get all variables from the view and save it to the controller
			this.initVariables(requ, ic);
			
			//Execute the required action, the Return object is already transfered to the jsonMapper in the controller and is not user anymore
			ic.execute(r ,action);
			
			jString = ic.getUniqueJson();
			
			
		}else{
			
			JSON j = new JSON();
			r.addSimpleError("No controller found for this action: "+ action);
			jString = j.messageConstruct(r);
			
		}
		
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
	
	
	protected List<IController> getControllerList(){
		List<IController> cList = new ArrayList<>();
		cList.add(new GenericController());
		cList.add(new CRUDController());
		return cList;
	}
	
	protected IController getController(Return r, String action){
		for(IController ic : this.getControllerList()){
			r = ic.validateAction(action);
			if(r.isSuccess()){
				return ic;
			}
		}
		
		return null;
	}
	
	
}
