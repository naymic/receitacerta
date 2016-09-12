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
	


	public ArrayList<Model> select(Model recipe, ArrayList<Model> ingredientes){

		//Remove the maxCalories value to filter after
		ReceitaView receita = (ReceitaView)recipe;
		Double maxC = receita.getMaxCalories();
		receita.setMaxCalories(null);

		ArrayList<Model> list = DAORelationList.getInstance().select(receita);
		
		//Create a filter to check if the maxCalorias are less than given from user
		if(maxC != null)
			list = this.filterByMaxCalories(maxC, list);
			

		//Create a filter to check if all ingredients informed by the user exist in the repices 
		if(ingredientes.size() > 0)
			list = this.filterRecipesByIngredientsGiven(list, ingredientes);
		
		return list;

	}

	
	public ArrayList<Model> filterByMaxCalories(Double maxCalories, ArrayList<Model> recipeList){
		java.sql.PreparedStatement stmt;
		ArrayList<Model> recipeReturnList = new ArrayList<>();
		
		if(recipeList.size() == 0)
			return recipeList;
		
 		
		ArrayList<String> idRecipeList = this.getPKListFromModelList(recipeList);
		
		String sql_string = "SELECT r.id,r.receita_rendimento_tipos_valor FROM receitas as r INNER JOIN pertence as p ON p.receitas_id=r.id INNER JOIN ingredientes as i ON p.ingredientes_id=i.id WHERE r.id IN("+ utils.StringUtils.getSubstitueCommaString(idRecipeList, "?")+ ") GROUP BY r.id HAVING (SUM(i.calorias*p.quantidade)/r.receita_rendimento_tipos_valor) < ?";	
		
		
		idRecipeList.add(maxCalories.toString());
		
		try {
			stmt = getDb().getCon().prepareStatement(sql_string);
			this.executeStatement(stmt, idRecipeList.toArray());
		
		
			ResultSet rs = stmt.executeQuery();
	
			while(rs.next()){
				Integer id = rs.getInt(1);
	
				for(Model model: recipeList){
					Receita recipe = (Receita)model;
					Integer recid = recipe.dgetId();
					if(id.intValue() == recipe.dgetId().intValue())
						recipeReturnList.add(recipe);
				}
	
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return recipeReturnList;
						
		
	}
	
	private ArrayList<Model> filterRecipesByIngredientsGiven(ArrayList<Model> recipesList, ArrayList<Model> ingredientsList){
		
		for(int i = 0; i < recipesList.size(); i ++){
			Receita recipe = (Receita) recipesList.get(i);
			
			if(!recipe.agetListaPertence().containsAll(ingredientsList)){
				recipesList.remove(recipe);
				i--;
			}
			
		}
		
		return recipesList;
	}

	
}
