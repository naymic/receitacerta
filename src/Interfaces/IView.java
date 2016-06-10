package Interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IView {
	
	public void process(HttpServletRequest req, HttpServletResponse resp);
	
	public void initControllerList();
	
	public String getAction(HttpServletRequest requ);

}
