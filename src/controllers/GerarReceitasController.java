package controllers;

import java.util.ArrayList;

import annotations.AControllerMethod;
import dao.DAOGerarReceita;
import jresponseclasses.JReturn;
import model.Ingredientes;
import model.Model;
import model.ReceitaView;

public class GerarReceitasController extends CrudController{
	
	
	
	
	@AControllerMethod(checkAttributes = false, needAuthentication = false)
	public JReturn formfilterAction(JReturn r, Model object){

		return this.novoAction(r, object);
		


	}
	
	
	
	@AControllerMethod(checkAttributes = false, needAuthentication = false)
	public JReturn buscaAction(JReturn r, Model object){
		
		
		ReceitaView recta = (ReceitaView)object;
		
		ArrayList<Model> ingredientsList = new ArrayList<>();
		try {
			if(recta.getStringIngredientesId() != null && recta.getStringIngredientesId().length() > 0)
				ingredientsList = getIngredientsList(recta.getStringIngredientesId());
		} catch (Exception e) {
			r.addSimpleError("24");//"The string with the id's of the ingredients has some error. String: "+ recta.getStringIngredientesId()
			e.printStackTrace();
		}
		ArrayList<Model> recipeList = DAOGerarReceita.getInstance().search(recta, ingredientsList, this.getPageNumber(), this.getOrderList());
		
		r.getData().setDataList(recipeList);
		
		return r;
		
	}
	

	
	
	private ArrayList<Model> getIngredientsList(String ingredientesIds) throws Exception{
		ArrayList<Model> ingredientsList= new ArrayList<>();
		String[] ingredientesVect = null;
		
		if(ingredientesIds != null && ingredientesIds.length() > 0)
			ingredientesVect = ingredientesIds.split(",");
		
		for(String s : ingredientesVect){
			Integer i = (Integer) converters.GenericConverter.convert(Integer.class, s);
			Ingredientes ingrediente = new Ingredientes();
			ingrediente.dsetId(i);
			ingredientsList.add(ingrediente);
		}
		return ingredientsList;
	}
	

	
}
