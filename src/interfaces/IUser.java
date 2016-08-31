package interfaces;

public interface IUser {
	/**
	 * Get the user id
	 * @return Integer
	 */
	public Integer getId();
	
	/**
	 * Set the user ID
	 * @param Integer id
	 */
	public void setId(Integer id);
	
	/**
	 * Get boolean to check if user is logged in
	 * @return boolean
	 */
	public boolean isLoggedin();
	
	/**
	 * Set the user logged or not
	 * @param loggedin boolean
	 */
	public void setLoggedin(boolean loggedin);
	
	/**
	 * Get the username
	 * @return
	 */
	public String getUsername();
	
	/**
	 * Set the username
	 * @param userName
	 */
	public void setUsername(String username);
	
	/**
	 * Set the path to the users image
	 * @return
	 */
	public String getImagepath();
	
	/**
	 * Get the path to the users image
	 * @param imagepath
	 */
	public void setImagepath(String imagepath);
	
	public void setSelf(IUser iu);
	
}
