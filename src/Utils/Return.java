package Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Model.Usuario;

public class Return {
	private HashMap<String, String> messageMap;
	private ArrayList<String> messages;
	private ArrayList<String> errors;
	private HashMap<String, String> redirect;
	private boolean loggedIn;

	private boolean success;
	

	
	public Return(){
		setSuccess(true);
		messageMap = new HashMap<String, String>();
		messages = new ArrayList<>();
		errors = new ArrayList<>();	
		redirect = new HashMap<>();
	}
	
	
	public void addMsg(String msg){
		messages.add(msg);
	}
	
	
	public void addSimpleError(String errorMsg){
		setSuccess(false);
		errors.add(errorMsg);
	}
	
	
	@SuppressWarnings("unused")
	private int getNextIndexOfSubKey(String subkey){
		int i;
		for(i=0; messageMap.containsKey(subkey+"_"+i); i++){
		}
		return i;
	}
	

	public void addAttributeError(String className, String attributeName, String errorMsg){
		setSuccess(false);
	
		messageMap.put("atb-"+className+"-"+attributeName,errorMsg);
	}
	
	
	public HashMap<String, String> getMessageMap(){return messageMap;}
	
	
	public boolean isSuccess(){return success;}
	public void setSuccess(boolean success){
		this.success = success;
	}
	
	public ArrayList<String> getSimpleErrors(){
		return errors;
	}
	
	public ArrayList<String> getMessage(){
		return messages;
	}
	
	
	
	
	
	public HashMap<String, String> getAttributeErrors(){
		HashMap<String, String> attributeErrors = new HashMap<>();
		Iterator<String> it = this.getMessageMap().keySet().iterator();
		String errorKey = null;
		
		while(it.hasNext()){
			errorKey = it.next();
			if(errorKey.contains("atb-"))
				attributeErrors.put(errorKey.substring(4),this.getMessageMap().get(errorKey));
		}
		
		return attributeErrors;
	}


	public HashMap<String, String> getRedirect() {
		return redirect;
	}


	public void setRedirect(String usecase, String action, String classname) {
		this.getRedirect().put("redirectUseCase", usecase);
		this.getRedirect().put("redirectAction", action);
		this.getRedirect().put("redirectClassname", classname);
	}
	
	public void setRedirect(HashMap<String, String> redirect) {
		this.redirect = redirect;
	}


	/**
	 * @return the usuario
	 */
	public boolean getLoggedIn() {
		return loggedIn;
	}


	/**
	 * @param usuario 
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	
	
}
