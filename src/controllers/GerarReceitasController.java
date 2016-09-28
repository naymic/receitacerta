package controllers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ListModel;

import annotations.AControllerMethod;
import dao.DAO;
import dao.DAOGerarReceita;
import dao.DAORelation;
import dao.DAORelationList;
import enums.ReturnType;
import exceptions.NoActionException;
import interfaces.IApplicationSession;
import interfaces.IController;
import interfaces.IUser;
import jresponseclasses.JReturn;
import model.Ingredientes;
import model.Model;
import model.Receita;
import model.ReceitaView;
import reflection.ReflectionDAO;
import reflection.ReflectionDAORelation;
import utils.RequestObject;

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
			r.addSimpleError("The string with the id's of the ingredients has some error. String: "+ recta.getStringIngredientesId());
			e.printStackTrace();
		}
		ArrayList<Model> recipeList = DAOGerarReceita.getInstance().search(recta, ingredientsList, this.getPageNumber());
		
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
