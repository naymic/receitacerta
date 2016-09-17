package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Model;
import model.Receita;
import model.ReceitaView;

public class DAOGerarReceita extends DAORelation {
	
	private static DAOGerarReceita dgr = null;
	

	public static DAOGerarReceita getInstance(){
		if(dgr == null){
			dgr = new DAOGerarReceita();
		}
		return dgr;
	}
	
	private DAOGerarReceita(){
		super();
	}
	


	public ArrayList<Model> search(Model recipe, ArrayList<Model> ingredientes){

		//Remove the maxCalories value to filter after
		ReceitaView receita = (ReceitaView)recipe;
		Double maxC = null;
		
		if(receita.dgetMaxCalories() != null){
			maxC = receita.dgetMaxCalories();
			receita.dsetMaxCalories(null);
		}

		ArrayList<Model> list = DAORelationList.getInstance().search(receita);
		
		//Create a filter to check if the maxCalorias are less than given from user
		if(maxC != null)
			list = this.filterByMaxCalories(maxC, list);
		
		//Create a filter to check if all ingredients informed by the user exist in the repices 
		if(ingredientes.size() > 0){
			list = this.filterRecipesByIngredientsGiven(list, ingredientes);
		}
		
		return list;

	}

	
	public ArrayList<Model> filterByMaxCalories(Double maxCalories, ArrayList<Model> recipeList){
		java.sql.PreparedStatement stmt;
		
		if(recipeList.size() == 0)
			return recipeList;
		
		
		for(int i = 0; i < recipeList.size() ; i++){
			ReceitaView rv = (ReceitaView)recipeList.get(i);
			
			if(rv.dgetMaxCalories() > maxCalories){
				recipeList.remove(i);
				i--;
			}
			
		}
		
		return recipeList;
						
		
	}
	
	private ArrayList<Model> filterRecipesByIngredientsGiven(ArrayList<Model> recipesList, ArrayList<Model> ingredientsList){
		
		for(int i = 0; i < recipesList.size(); i ++){
			ReceitaView recipe = (ReceitaView) recipesList.get(i);
			
			if(!recipe.agetListaPertence().containsAll(ingredientsList)){
				recipesList.remove(recipe);
				i--;
			}
			
		}
		
		return recipesList;
	}

	
}
