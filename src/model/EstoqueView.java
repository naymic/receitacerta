package model;

import annotations.AModelClasses;
import annotations.Entity;


@AModelClasses(needUserObject = true)
public class EstoqueView extends EstoqueParent {
	
	String ingredientesId;


	@Entity(attributeName="ingredientes_id", fk=true)
	public String dgetIngredientesId() {
		return ingredientesId;
	}

	public void dsetIngredientesId(String ingredientesId) {
		this.ingredientesId = ingredientesId;
	}
}
