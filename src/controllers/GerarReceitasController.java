package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ListModel;

import dao.DAOGerarReceita;
import dao.DAORelation;
import dao.DAORelationList;
import exceptions.NoActionException;
import interfaces.IApplicationSession;
import interfaces.IController;
import interfaces.IUser;
import jsonclasses.JObject;
import jsonclasses.JReturn;
import model.Ingredientes;
import model.Model;
import model.Receita;
import model.ReceitaView;

public class GerarReceitasController extends GenericController{
	
	
	
	
	public JReturn buscaAction(JReturn r, Model object){
		
		
		ReceitaView recta = (ReceitaView)object;
		
		ArrayList<Model> ingredientsList = new ArrayList<>();
		try {
			ingredientsList = getIngredientsList(recta.getStringIngredientesId());
		} catch (Exception e) {
			r.addSimpleError("The string with the id's of the ingredients has some error. String: "+ recta.getStringIngredientesId());
			e.printStackTrace();
		}
		ArrayList<Model> recipeList = DAOGerarReceita.getInstance().select(recta, ingredientsList);
		
		r.getData().setDataList(recipeList);
		
		return r;
		
	}
	

	
	
	private ArrayList<Model> getIngredientsList(String ingredientesIds) throws Exception{
		ArrayList<Model> ingredientsList= new ArrayList<>();
		String[] ingredientesVect = ingredientesIds.split(",");
		
		for(String s : ingredientesVect){
			Integer i = (Integer) converters.GenericConverter.convert(Integer.class, s);
			Ingredientes ingrediente = new Ingredientes();
			ingrediente.dsetId(i);
			ingredientsList.add(ingrediente);
		}
		return ingredientsList;
	}
	

	@Override
	public boolean needAuthentication() {
		// TODO Auto-generated method stub
		return false;
	}
}
