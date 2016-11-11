package controllers;

import annotations.AControllerMethod;
import dao.DAO;
import jresponseclasses.JReturn;
import model.Model;

public class IngredientesController extends CrudController {

	
	@Override
	@AControllerMethod(checkAttributes = true, needAuthentication = true)
	public JReturn salvarAction(JReturn r, Model object) {
		
		
		return super.salvarAction(r, object);
	}
	
	
}
