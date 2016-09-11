package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Model;
import model.Receita;

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
	
	
	
	
	
	public ArrayList<Model> filterByMaxCalories(Double maxCalories, ArrayList<Model> recipeList){
		java.sql.PreparedStatement stmt;
		ArrayList<Model> recipeReturnList = new ArrayList<>();
		
		if(recipeList.size() == 0)
			return recipeList;
		
		
		ArrayList<String> idRecipeList = this.getPKListFromModelList(recipeList);
		
		String sql_string = "SELECT r.id FROM receitas as r INNER JOIN pertence as p ON p.receitas_id=r.id INNER JOIN ingredientes as i ON p.ingredientes_id=i.id WHERE r.id IN("+ utils.StringUtils.getSubstitueCommaString(idRecipeList, "?")+ ") HAVING (SUM(i.calorias*p.quantidade)/r.receita_rendimento_tipos_valor	) < ?";	
		
		
		idRecipeList.add(maxCalories.toString());
		
		try {
			stmt = getDb().getCon().prepareStatement(sql_string);
			this.executeStatement(stmt, idRecipeList.toArray());
		
		
			ResultSet rs = stmt.executeQuery();
	
			while(rs.next()){
				Integer id = rs.getInt(1);
	
				for(Model model: recipeList){
					Receita recipe = (Receita)model;
					if(id == recipe.dgetId())
						recipeReturnList.add(recipe);
				}
	
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return recipeReturnList;
						
		
	}
	

	
}
