package JsonClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Enums.ReturnType;
import Interfaces.IUser;
import Model.Model;

public class JReturn{
	boolean success;
	private JUser user;
	private JRedirect redirect;
	private ArrayList<JAttributeError> atberrors;
	private ArrayList<String> messages;
	private ArrayList<String> errors;
	private JData data;
	private ReturnType returnType;
	
	public JReturn(){
		setSuccess(true);
		data = new JData();
		redirect = new JRedirect();
		user = new JUser();
		atberrors = new ArrayList<>();
		messages = new ArrayList<>();
		errors = new ArrayList<>();	
		returnType = ReturnType.INFO;
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

	public ReturnType getReturnType() {
		return returnType;
	}

	public void setReturnType(ReturnType returnType) {
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

	
}
