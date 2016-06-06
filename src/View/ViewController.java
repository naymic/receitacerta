package View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
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
	

	
	protected void process(HttpServletRequest requ, HttpServletResponse resp, String content){
		Return r = new Return();
		String action = this.getAction(requ);
		Model object = this.initObj(requ);
		
		IController ic = getController(r, action);
		r = ic.execute(action);
		
		if(){
			
		}
		
	}
	
	

	
	protected Model initObj(HttpServletRequest requ){
			
		
		if(requ.getParameter("className").length() > 0){
			Enumeration<String> e = requ.getParameterNames();
			String paramName;
			
			String className = requ.getParameter("className");
			Model obj = ReflectionDAO.instanciateObjectByName(className);	
			ReflectionDAORelation rd = new ReflectionDAORelation(obj);
			
			
			while(e.hasMoreElements()){
				paramName = e.nextElement();
				if(paramName.contains(className+".") && requ.getParameter(paramName).length() > 0){
					rd.setValueFromAttributename(obj, paramName.split(".")[1], requ.getParameter(paramName));
				}
			}
			return obj;
		}
		
			
		return null;
	}
	
	protected String getAction(HttpServletRequest request){
		return request.getParameter("action");		
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
