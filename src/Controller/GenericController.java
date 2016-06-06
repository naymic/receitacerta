package Controller;

import java.util.ArrayList;
import java.util.List;

import Interfaces.IController;
import Utils.Return;

public class GenericController implements IController{
	String action;

	ArrayList<String> validActions;
	
	

	@Override
	public void setAction(String action) {
		this.action = action;		
	}

	@Override
	public String getAction(String action) {
		return this.action;
	}
	
	@Override
	public Return validateAction(String action) {
		Return r = new Return();
		for(String s : getValidActionsList()){
			if(s.equalsIgnoreCase(action)){
				return r;
			}
			
		}
		
		r.addError("Action dont exist in: "+ this.getClass() +" action:"+ action);
		return r;

	}

	@Override
	public Return execute(String action) {
		this.setAction(action);
		Return r = this.validateAction(action);
		
		return r;
	}

	@Override
	public List<String> getValidActionsList() {
		validActions = new ArrayList<String>();
		validActions.add("index");
		
		return validActions;
	}


	@Override
	public boolean needAuthentication() {
		return false;
	}

	@Override
	public boolean dontShowCommonPage() {
		return false;
	}

}
