package JsonClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Enums.ReturnType;
import Interfaces.IUser;
import Model.Model;

public class JReturn {
	boolean success;
	private JUser user;
	private JRedirect redirect;
	private ArrayList<JAttributeError> atberror;
	private ArrayList<String> messages;
	private ArrayList<String> errors;
	private JData data;
	private ReturnType returnType;
	
	public JReturn(){
		setSuccess(true);
		redirect = new JRedirect();
		user = new JUser();
		atberror = new ArrayList<>();
		messages = new ArrayList<>();
		errors = new ArrayList<>();	
		data = new JData();
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
		
		this.atberror.add(auxAtbError);
	}
	
	public void setRedirect(JRedirect redirect){
		this.redirect=redirect;
	}

	public JData getData() {
		return data;
	}

	public void setData(JData data) {
		this.data = data;
	}

	public ReturnType getReturnType() {
		return returnType;
	}

	public void setReturnType(ReturnType returnType) {
		this.returnType = returnType;
	}

	
	
	
	
}
