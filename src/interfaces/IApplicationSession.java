package interfaces;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public interface IApplicationSession {
	
	
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
	
	/**
	 * Check if a Attribute exist
	 */
	public boolean existMapAttribute(String name);
	
}
