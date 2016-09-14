package model;

import java.util.ArrayList;

import annotations.AModelClasses;
import annotations.Entity;

@AModelClasses(needUserObject = false)
public class ReceitaView extends Receita{
	

	private Double maxCalories;
	private String stringIngredientesId;
	
	
	public ReceitaView(){
		super();
	}
	
	public String getTableName() {
		return "view_receitas";
	}
	
	@Entity(attributeName="total_cal", required=false)
	public Double dgetMaxCalories() {
		return maxCalories;
	}

	public void dsetMaxCalories(Double max_calories) {
		this.maxCalories = max_calories;
	}	
	
	@Entity(attributeName="", required=false)
	public String getStringIngredientesId() {
		return stringIngredientesId;
	}

	public void setStringIngredientesId(String stringIngredientesId) {
		this.stringIngredientesId = stringIngredientesId;
	}
	
}
