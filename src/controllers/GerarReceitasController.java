package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ListModel;

import dao.DAORelationList;
import exceptions.NoActionException;
import interfaces.IApplicationSession;
import interfaces.IController;
import interfaces.IUser;
import jsonclasses.JObject;
import jsonclasses.JReturn;
import model.Model;
import model.Receita;
import model.ReceitaView;

public class GerarReceitasController extends GenericController{
	
	
	
	
	public JReturn buscaAction(JReturn r, Model object){
		
		//Remove the maxCalories value to filter after
		ReceitaView receitaView = (ReceitaView)object;
		Double maxC = receitaView.dgetCaloriasTotal();
		receitaView.dsetCaloriasTotal(null);
		
		ArrayList<Model> list = DAORelationList.getInstance().select(object);
		
		
		//Create a filter to check if the maxCalorias are less than given from user
		
		
		//Create a filter to check if all ingredients informed by the user exist in the repices 
		
		return r;
		
	}
	
	public List<Model> searchRecipesWithLessCalories(Double maxCalories, List<Model> listModel){
		for(int i = 0; i < listModel.size(); i++){
			ReceitaView recipeModel = (ReceitaView) listModel.get(i);
			if(recipeModel.dgetCaloriasTotal() > maxCalories){
				listModel.remove(i);
				i--;
			}
		}
		return listModel;
	}
	
	public List<Model> searchRecipesByIngredientsGiven(List<Model> ingredientsList){
		
		List<Model> recipesList = DAORelationList.getInstance().select(new Receita());
		
		for(int i = 0; i < recipesList.size(); i ++){
			Receita recipe = (Receita) recipesList.get(i);
			
			if(!recipe.agetListaPertence().containsAll(ingredientsList)){
				recipesList.remove(recipe);
				i--;
			}
			
		}
		
		return recipesList;
	}
	

	@Override
	public boolean needAuthentication() {
		// TODO Auto-generated method stub
		return false;
	}
}
