package Interfaces;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public interface IApplicationSesssion {
	
	public HttpSession session = null;
	public HashMap<String, Object> sessionMap = null;
	
	/**
	 * Receive the class intern session
	 * @return
	 */
	HttpSession getSession();
	
	/**
	 * Set the session for getting values
	 * @param session
	 */
	void setSession(HttpSession session);
	
	
	/**
	 * Get class intern session variable map
	 * @return
	 */
	public HashMap<String, Object> getSessionMap();
	
	/**
	 * Set a specifiv attribute by it's name and value
	 * @param name
	 * @param value
	 */
	public void setMapAttribute(String name, Object value);
	
	/**
	 * Get a specific attribute value by it's name
	 * @param name
	 * @return
	 */
	public Object getMapAttribute(String name);
	
}
