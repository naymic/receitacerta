package model;

import annotations.AModelClasses;
import annotations.Entity;
import jresponseclasses.JReturn;


@AModelClasses(needUserObject = false)
public class Ingredientes extends Model{

	private Integer id;
	private String nome;
	private Double calorias;
	private IngredienteUnidades ingredientesUnidade;
	private IngredienteTipo ingredientesTipoId;
	private IngredienteArmazenamentos ingredienteArmazenamentosId;
	/*private Integer importancia;
	private Integer gluten;
	private String anotacoes;*/
	
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "ingredientes";
	}

	@Override
	public void verify(JReturn r) {
		int compare = 0;
		
		if(this.dgetCalorias() != null)
			compare = Double.compare(this.dgetCalorias(), new Double(0.0));
		
		if(this.dgetCalorias() != null && compare < 0 ){
			r.addAttributeError(this.getClass().getSimpleName(), "calorias", "Attribute calorias cannot be negative: "+ this.dgetCalorias());
		}

	}
	
	@Entity(attributeName = "id", pk=true)
	public Integer dgetId() {
		return id;
	}

	public void dsetId(Integer id) {
		this.id = id;
	}
	
	@Entity(attributeName = "nome")
	public String dgetNome() {
		return nome;
	}

	public void dsetNome(String nome) {
		this.nome = nome;
	}
	
	@Entity(attributeName = "calorias")
	public Double dgetCalorias() {
		return calorias;
	}

	public void dsetCalorias(Double calorias) {
		this.calorias = calorias;
	}
	
	
	@Entity(attributeName = "ingredientes_unidades_id", fk=true)
	public IngredienteUnidades dgetIngredientesUnidade() {
		return ingredientesUnidade;
	}

	public void dsetIngredientesUnidade(IngredienteUnidades ingredientesUnidade) {
		this.ingredientesUnidade = ingredientesUnidade;
	}

	@Entity(attributeName = "ingredientes_tipo_id", fk=true)
	public IngredienteTipo dgetIngredientesTipoId() {
		return ingredientesTipoId;
	}

	public void dsetIngredientesTipoId(IngredienteTipo ingredientesTipoId) {
		this.ingredientesTipoId = ingredientesTipoId;
	}

	@Entity(attributeName = "ingrediente_armazenamentos_id", fk=true)
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
