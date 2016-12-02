package messageclient;

import java.util.ArrayList;

public class JMessageResponse {

	private boolean success;
	private ArrayList<JMessageResponseItem> responseItems;
	private ArrayList<String> errorMsgs;
	private ArrayList<Integer> htmlErrorCodes;
	public boolean isSuccess() {
		return success;
	}
	
	
	JMessageResponse(){
		this.setErrorMsgs(new ArrayList<String>());
		this.setResponseItems(new ArrayList<JMessageResponseItem>());
		this.setHtmlErrorCodes(new ArrayList<Integer>());
	}
	
	public String getMessageFromIdFromReturn(String id){
		
		for(JMessageResponseItem jmri : this.getResponseItems()){
			String msgid = jmri.getIdmsg().toString();
			
			if(id.equals(msgid)){
				return jmri.getMessage();
			}
		}
		
		return null;
	}
	
	
	/*** Setters && Getters ***/
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public ArrayList<JMessageResponseItem> getResponseItems() {
		return responseItems;
	}
	public void setResponseItems(ArrayList<JMessageResponseItem> responseItems) {
		this.responseItems = responseItems;
	}
	public ArrayList<String> getErrorMsgs() {
		return errorMsgs;
	}
	public void setErrorMsgs(ArrayList<String> errorMsgs) {
		this.errorMsgs = errorMsgs;
	}
	public ArrayList<Integer> getHtmlErrorCodes() {
		return htmlErrorCodes;
	}
	public void setHtmlErrorCodes(ArrayList<Integer> htmlErrorCodes) {
		this.htmlErrorCodes = htmlErrorCodes;
	}
	
	
	
	
}
