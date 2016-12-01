package model;

import annotations.AModelClasses;
import annotations.AEntity;
import jresponseclasses.JReturn;


@AModelClasses(needUserObject = false)
public class Ingredientes extends IngredientesParent{

	
	private IngredienteUnidades ingredientesUnidade;
	private IngredienteTipo ingredientesTipoId;
	private IngredienteArmazenamentos ingredienteArmazenamentosId;

	
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "ingredientes";
	}


	
	
	@AEntity(attributeName = "ingredientes_unidades_id", fk=true)
	public IngredienteUnidades dgetIngredientesUnidade() {
		return ingredientesUnidade;
	}

	public void dsetIngredientesUnidade(IngredienteUnidades ingredientesUnidade) {
		this.ingredientesUnidade = ingredientesUnidade;
	}

	@AEntity(attributeName = "ingredientes_tipo_id", fk=true)
	public IngredienteTipo dgetIngredientesTipoId() {
		return ingredientesTipoId;
	}

	public void dsetIngredientesTipoId(IngredienteTipo ingredientesTipoId) {
		this.ingredientesTipoId = ingredientesTipoId;
	}

	@AEntity(attributeName = "ingrediente_armazenamentos_id", fk=true)
	public IngredienteArmazenamentos dgetIngredienteArmazenamentosId() {
		return ingredienteArmazenamentosId;
	}


	public void dsetIngredienteArmazenamentosId(IngredienteArmazenamentos ingredienteArmazenamentosId) {
		this.ingredienteArmazenamentosId = ingredienteArmazenamentosId;
	}

	/*@Entity(attributeName= "importancia")
	public Integer dgetImportancia() {
		return importancia;
	}

	
	public void dsetImportancia(Integer importancia) {
		this.importancia = importancia;
	}

	@Entity(attributeName= "gluten", required=false)
	public Integer dgetGluten() {
			return gluten;
	}


	public void dsetGluten(Integer gluten) {
		this.gluten = gluten;
	}

	@Entity(attributeName= "anotacoes", required=false)
	public String dgetAnotacoes() {
		return anotacoes;
	}

	
	public void dsetAnotacoes(String anotacoes) {
		this.anotacoes = anotacoes;
	}*/

	
}
