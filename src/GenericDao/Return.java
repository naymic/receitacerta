package GenericDao;

import java.util.ArrayList;

public class Return {
	private ArrayList<String> arl;
	private boolean success;
	

	
	public Return(){
		setSuccess(true);
		arl = new ArrayList<String>();
	}
	
	
	public void addError(String errorMsg){
		setSuccess(false);
		arl.add(errorMsg);
	}
	public ArrayList<String> getErrors(){return arl;}
	
	
	public boolean isSuccess(){return success;}
	public void setSuccess(boolean success){this.success = success;}
	
	
}
