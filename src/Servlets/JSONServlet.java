package Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JSONServlet
 */
public class JSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        
      String json =  request.getParameter("json");
        // 2. initiate jackson mapper
        Object mapper = new Object();
 
        // 3. Convert received JSON to Article
        System.out.println(json);
 
        // 4. Set response type to JSON
        response.setContentType("application/json");            
 
        // 5. Add article to List<Article>
        String d = "{"
	+ "\"busca\":{"
	+ "	\"ingrediente\":{ "
	+ "		\"status\":true,\"resultado\":[{\texto\":\"jkljklö\"}]"
	+ " }"
	+ "}"
	+ "}";
 
        // 6. Send List<Article> as JSON to client
        response.getOutputStream().print(d);
	};

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		 // 1. get received JSON data from request
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
            json = br.readLine();
        }
 
        // 2. initiate jackson mapper
        Object mapper = new Object();
 
        // 3. Convert received JSON to Article
        System.out.println(json);
 
        // 4. Set response type to JSON
        response.setContentType("application/json");            
 
        // 5. Add article to List<Article>
        String d = "{"
	+ "\"busca\":{"
	+ "	\"ingrediente\":{ "
	+ "		\"status\":true,\"resultado\":[{\texto\":\"jkljklö\"}]"
	+ " }"
	+ "}"
	+ "}";
 
        // 6. Send List<Article> as JSON to client
        response.getOutputStream().print(d);
	}

}
