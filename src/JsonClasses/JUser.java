package JsonClasses;

import Interfaces.IUser;

public class JUser implements IUser {
	
	Integer id;
	boolean loggedin;
	String username;
	String imagepath;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public boolean isLoggedin() {
		return loggedin;
	}
	
	public void setLoggedin(boolean loggedin) {
		this.loggedin = loggedin;
	}

	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public void setUsername(String username) {
		this.username=username;
	}
	
	@Override
	public void setSelf(IUser iu) {
		this.setId(iu.getId());
		this.setLoggedin(iu.isLoggedin());
		this.setUsername(iu.getUsername());
		this.setImagepath(iu.getImagepath());
	}
	
	
	
	
}
