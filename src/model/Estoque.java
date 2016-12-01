package model;

import annotations.AModelClasses;
import annotations.AEntity;

/***
 * 
 * @author micha
 *
 * Class to know the personal foodstock at home
 *
 */
@AModelClasses(needUserObject = true)
public class Estoque extends EstoqueParent {
	
	
	Ingredientes ingredientesId;

	

	@AEntity(attributeName="ingredientes_id", fk=true)
	public Ingredientes dgetIngredientesId() {
		return ingredientesId;
	}

	public void dsetIngredientesId(Ingredientes ingredientesId) {
		this.ingredientesId = ingredientesId;
	}
	
	
	
	

}
