package jresponseclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import enums.EReturnType;
import interfaces.IUser;
import model.Model;

/**
 * 
 * @author micha
 *
 * Main class to response to a request
 * Standarizes the structure of the response
 *
 */
public class JReturn{
	
	//Returns if the request was successful or not
	boolean success;
	
	//Returns a user object with some information about it
	private JUser user;
	
	//if redirec.redirect is true, the response calls for a login
	private JRedirect redirect;
	
	//errors relating to an attribute of the request object
	private ArrayList<JAttributeError> atberrors;
	
	//Normal messages
	private ArrayList<String> messages;
	
	//Error messages
	private ArrayList<String> errors;
	
	//Data object for tables and forms
	private JData data;
	
	//Get a specified list of informations about this API
	private JDiscovery discover;
	
	//Sets the return type
	private EReturnType returnType;
	
	public JReturn(){
		setSuccess(true);
		data = new JData();
		redirect = new JRedirect();
		user = new JUser();
		atberrors = new ArrayList<>();
		messages = new ArrayList<>();
		errors = new ArrayList<>();	
		returnType = EReturnType.INFO;
	}
	
	public void setUser(IUser iu){
		user.setSelf(iu);
	}
	
	public JUser getUser(){return user;}
	
	
	public void addMsg(String msg){
		messages.add(msg);
	}
	
	
	public void addSimpleError(String errorMsg){
		setSuccess(false);
		errors.add(errorMsg);
	}
	

	public void setSuccess(boolean b) {
		this.success=b;
		
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void addAttributeError(String classname, String attributename, String errorMsg){
		setSuccess(false);
		
		JAttributeError auxAtbError = new JAttributeError();
		auxAtbError.setAttributename(attributename);
		auxAtbError.setClassname(classname);
		auxAtbError.setError(errorMsg);
		
		this.atberrors.add(auxAtbError);
	}
	
	public void setRedirect(JRedirect redirect){
		this.redirect=redirect;
	}
	
	
	public JData getData() {
		return data;
	}
	
	public JRedirect getRedirect() {
		return redirect;
	}

	/*Deactivation for do not allow set data directly
	 * public void setData(JData data) {
		this.data = data;
	}
	public void setData(JData data) {
		this.data = data;
	}*/

	public EReturnType getReturnType() {
		return returnType;
	}

	public void setReturnType(EReturnType returnType) {
		this.returnType = returnType;
	}

	public ArrayList<String> getSimpleErrors() {
		return errors;
	}

	public List<String> getMessages() {
		return messages;
	}

	public ArrayList<JAttributeError> getAtberrors() {
		return atberrors;
	}

	public JDiscovery getDiscover() {
		return discover;
	}

	public void setDiscover(JDiscovery discover) {
		this.discover = discover;
	}

	
}
