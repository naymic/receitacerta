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
		return "receitas";
	}
	
	@Entity(attributeName="", required=false)
	public Double getMaxCalories() {
		return maxCalories;
	}

	public void setMaxCalories(Double max_calories) {
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
