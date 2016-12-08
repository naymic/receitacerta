package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jrequestclasses.JOrder;
import model.Ingredientes;
import model.Model;
import model.Pertence;
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
	


	public ArrayList<Model> search(Model recipe, ArrayList<Model> ingredientes, Integer page, ArrayList<JOrder> orderList){

		//Remove the maxCalories value to filter after
		ReceitaView receita = (ReceitaView)recipe;
		Double maxC = null;
		
		if(receita.dgetMaxCalories() != null){
			maxC = receita.dgetMaxCalories();
			receita.dsetMaxCalories(null);
		}

		ArrayList<Model> list = DAORelationList.getInstance().search(receita, 1,  page, orderList);
		
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
		
		if(recipeList.size() == 0)
			return recipeList;
		
		
		for(int i = 0; i < recipeList.size() ; i++){
			ReceitaView rv = (ReceitaView)recipeList.get(i);
			
			if(rv.dgetMaxCalories() == null || rv.dgetMaxCalories() > maxCalories){
				recipeList.remove(i);
				i--;
			}
			
		}
		
		return recipeList;
						
		
	}
	
	
	private ArrayList<Model> filterRecipesByIngredientsGiven(ArrayList<Model> recipesList, ArrayList<Model> ingredientsList){
		
		for(int i = 0; i < recipesList.size(); i ++){
			ReceitaView recipe = (ReceitaView) recipesList.get(i);
			ArrayList<Model> ingredientes = this.getIngredientesListFromPertence(recipe.agetListaPertence());
			
			if(!checkIngredients(ingredientsList, ingredientes)){
				recipesList.remove(i);
				i--;
			}
			
			
		}
		
		return recipesList;
	}

	private boolean checkIngredients(ArrayList<Model> ingredientsList, ArrayList<Model> ingrFromRecipe) {
		boolean test;
		
		try{
			for(int j=0; j < ingrFromRecipe.size(); ++j){
				Ingredientes ingRecipe = (Ingredientes)ingrFromRecipe.get(j);
				
				test = false;
				for(Model modelFromList: ingredientsList){
					Ingredientes ingFromList = (Ingredientes)modelFromList;
					
					if(ingFromList.dgetId().intValue() == ingRecipe.dgetId().intValue()){
						test = true;
						break;
					}
					
				}
				
				if(!test){
					return false;
				}
				
			}
			
			return true;
			
		}catch(NullPointerException npe){
			return false;
		}
		
	}
	
	private ArrayList<Model> getIngredientesListFromPertence(ArrayList<Pertence> listPertence){
		ArrayList<Model> listIngredientes = new ArrayList<>();
		
		for(Pertence pertence : listPertence){
			listIngredientes.add(pertence.dgetIngrediente());
			
		}
		
		
		return listIngredientes;
	}
	
	

	
}
