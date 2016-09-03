package controllers;

import java.util.ArrayList;
import java.util.List;

import interfaces.IController;
import jsonclasses.JReturn;

public class GerarReceitasController extends GenericController {

	
	@Override
	public void execute(JReturn r, String action){
		
		
		if(action.equalsIgnoreCase("busca")){
			
			
			
		}
		
		
	}	
	
	
	@Override
	/**
	 * Gets the list of valid actions for this controller
	 */
	public List<String> getValidActionsList() {
		validActions = new ArrayList<String>();
		
		//Busca normal
		validActions.add("busca");
			
		return validActions;
	}
	
	

	
}
