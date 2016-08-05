package Model;

import Annotations.Entity;
import JsonClasses.JReturn;

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
	public JReturn verify(JReturn r) {
		super.verify(r);
		int compare = Double.compare(this.dgetCalorias(), new Double(0.0));
		
		if(this.dgetCalorias() != null && compare < 0 ){
			r.addAttributeError(this.getClass().getSimpleName(), "calorias", "Attribute calorias cannot be negative: "+ this.dgetCalorias());
		}
		return r;
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
	public IngredienteTipo dgetIngredientesTipo() {
		return ingredientesTipoId;
	}

	public void dsetIngredientesTipo(IngredienteTipo ingredientesTipoId) {
		this.ingredientesTipoId = ingredientesTipoId;
	}

	@Entity(attributeName = "ingrediente_armazenamentos_id", fk=true)
	public IngredienteArmazenamentos dgetIngredienteArmazenamentos() {
		return ingredienteArmazenamentosId;
	}


	public void dsetIngredienteArmazenamentos(IngredienteArmazenamentos ingredienteArmazenamentosId) {
		this.ingredienteArmazenamentosId = ingredienteArmazenamentosId;
	}



	
}
