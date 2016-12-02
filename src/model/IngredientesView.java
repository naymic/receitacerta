package model;


import annotations.AModelClasses;
import annotations.AEntity;

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

	@AEntity(attributeName = "ingredientes_unidades_id", fk=true)
	public String dgetIngredientesUnidade() {
		return ingredientesUnidade;
	}


	public void dsetIngredientesUnidade(String ingredientesUnidade) {
		this.ingredientesUnidade = ingredientesUnidade;
	}

	@AEntity(attributeName = "ingredientes_tipo_id", fk=true)
	public String dgetIngredientesTipoId() {
		return ingredientesTipoId;
	}


	public void dsetIngredientesTipoId(String ingredientesTipoId) {
		this.ingredientesTipoId = ingredientesTipoId;
	}

	@AEntity(attributeName = "ingrediente_armazenamentos_id", fk=true)
	public String dgetIngredienteArmazenamentosId() {
		return ingredienteArmazenamentosId;
	}


	public void dsetIngredienteArmazenamentosId(String ingredienteArmazenamentosId) {
		this.ingredienteArmazenamentosId = ingredienteArmazenamentosId;
	}

}
