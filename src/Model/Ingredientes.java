package Model;

import java.util.ArrayList;

import Reflection.Entity;
import Utils.Return;

public class Ingredientes extends Model{

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
	public Return verify() {
		Return r = super.verify();
		
		if(this.dgetCalorias() < 0.0){
			r.addError("Attribute calorias cannot be negative: "+ this.dgetCalorias());
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
	
	
	@Entity(attributeName = "ingredientes_unidades_id", fk=true, fk_tablename="ingrediente_unidades")
	public IngredienteUnidades dgetIngredientesUnidade() {
		return ingredientesUnidade;
	}

	public void dsetIngredientesUnidade(IngredienteUnidades ingredientesUnidade) {
		this.ingredientesUnidade = ingredientesUnidade;
	}

	@Entity(attributeName = "ingredientes_tipo_id", fk=true, fk_tablename="ingrediente_tipo")
	public IngredienteTipo dgetIngredientesTipo() {
		return ingredientesTipoId;
	}

	public void dsetIngredientesTipo(IngredienteTipo ingredientesTipoId) {
		this.ingredientesTipoId = ingredientesTipoId;
	}

	@Entity(attributeName = "ingrediente_armazenamentos_id", fk=true, fk_tablename="ingrediente_armazenamentos")
	public IngredienteArmazenamentos dgetIngredienteArmazenamentos() {
		return ingredienteArmazenamentosId;
	}


	public void dsetIngredienteArmazenamentos(IngredienteArmazenamentos ingredienteArmazenamentosId) {
		this.ingredienteArmazenamentosId = ingredienteArmazenamentosId;
	}


	
}
