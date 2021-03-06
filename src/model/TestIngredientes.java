package model;

import annotations.AModelClasses;
import annotations.AEntity;
import jresponseclasses.JReturn;


@AModelClasses(needUserObject = false)
public class TestIngredientes extends Model{

	private Integer id;
	private String nome;
	private Double calorias;
	private IngredienteUnidades ingredientesUnidade;
	private IngredienteTipo ingredientesTipoId;
	private IngredienteArmazenamentos ingredienteArmazenamentosId;

	
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "ingredientes";
	}

	@Override
	public void verify(JReturn r) {
		
		int compare = Double.compare(this.dgetCalorias(), new Double(0.0));
		
		if(this.dgetCalorias() != null && compare < 0 ){
			r.addAttributeError(this.getClass().getSimpleName(), "calorias", "Attribute calorias cannot be negative: "+ this.dgetCalorias());
		}
		
	}
	
	@AEntity(attributeName = "id", pk=true)
	public Integer dgetId() {
		return id;
	}

	public void dsetId(Integer id) {
		this.id = id;
	}
	
	@AEntity(attributeName = "nome")
	public String dgetNome() {
		return nome;
	}

	public void dsetNome(String nome) {
		this.nome = nome;
	}
	
	@AEntity(attributeName = "calorias")
	public Double dgetCalorias() {
		return calorias;
	}

	public void dsetCalorias(Double calorias) {
		this.calorias = calorias;
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



	
}
