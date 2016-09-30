package model;

import annotations.AModelClasses;
import annotations.Entity;


@AModelClasses(needUserObject = true)
public class EstoqueView extends EstoqueParent {
	
	String ingredientesId;
	
	@Override
	public String getTableName() {
		return "view_estoque";
	}

	@Entity(attributeName="ingredientes_id")
	public String dgetIngredientesId() {
		return ingredientesId;
	}

	public void dsetIngredientesId(String ingredientesId) {
		this.ingredientesId = ingredientesId;
	}
}
