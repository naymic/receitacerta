package model;


import annotations.AModelClasses;
import annotations.Entity;

@AModelClasses(needUserObject = false)
public class IngredientesView extends IngredientesParent{

	private String ingredientesUnidade;
	private String ingredientesTipoId;
	private String ingredienteArmazenamentosId;
	
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "ingredientes_view";
	}

	@Entity(attributeName = "ingrediente_armazenamentos_id", fk=true)
	public String dgetIngredientesUnidade() {
		return ingredientesUnidade;
	}


	public void dsetIngredientesUnidade(String ingredientesUnidade) {
		this.ingredientesUnidade = ingredientesUnidade;
	}

	@Entity(attributeName = "ingrediente_armazenamentos_id", fk=true)
	public String dgetIngredientesTipoId() {
		return ingredientesTipoId;
	}


	public void dsetIngredientesTipoId(String ingredientesTipoId) {
		this.ingredientesTipoId = ingredientesTipoId;
	}

	@Entity(attributeName = "ingrediente_armazenamentos_id", fk=true)
	public String dgetIngredienteArmazenamentosId() {
		return ingredienteArmazenamentosId;
	}


	public void dsetIngredienteArmazenamentosId(String ingredienteArmazenamentosId) {
		this.ingredienteArmazenamentosId = ingredienteArmazenamentosId;
	}

}
