package controllers;

import java.util.ArrayList;

import annotations.AControllerMethod;
import dao.DAO;
import dao.DAORelation;
import jresponseclasses.JReturn;
import model.Ingredientes;
import model.Model;

public class IngredientesController extends CrudController {

	
	@Override
	@AControllerMethod(checkAttributes = true, needAuthentication = true)
	public JReturn salvarAction(JReturn r, Model object) {
		Ingredientes obj = (Ingredientes)object;
		
		
		Ingredientes ingr = new Ingredientes();
		ingr.dsetNome(obj.dgetNome());
		ingr.dsetIngredientesUnidade(ingr.dgetIngredientesUnidade());
		
		ArrayList<Model> listIngredientes = DAORelation.getInstance().select(ingr);
		if(listIngredientes.size()==0){
			return super.salvarAction(r, object);	
		}else{
			r.addSimpleError("24");//Ingrediente com essa unidade já existente!
		}
		
		return r;
		
		
	}
	
	
}
