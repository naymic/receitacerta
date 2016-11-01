package interfaces;

public interface IApplicationSession<E>{
	
	
	/**
	 * Receive the class intern session
	 * @return
	 */
	public E getSession();
	
	/**
	 * Set the session for getting values
	 * @param session
	 */
	public void setSession(E session);
	
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