package Model;

import Reflection.Entity;
import Utils.Return;

public class Ingredientes extends Model{

	private Integer id;
	private String nome;
	private Double calorias;
	private Integer ingredientes_unidade_id1;
	private Integer ingredientes_tipo_id;
	private Integer ingrediente_armazenamentos_id;
	
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "ingredientes";
	}

	@Override
	public Return verify() {
		Return r = super.verify();
		
		if(this.dgetCalorias() < 0.0){
			r.addError("Attribute calorias cannot be negative");
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
	
	@Entity(attributeName = "ingredientes_unidade_id1", fk=true, fk_tablename="ingrediente_unidades")
	public Integer dgetIngredientes_unidade_id1() {
		return ingredientes_unidade_id1;
	}

	public void dsetIngredientes_unidade_id1(Integer ingredientes_unidade_id1) {
		this.ingredientes_unidade_id1 = ingredientes_unidade_id1;
	}
	
	@Entity(attributeName = "ingredientes_tipo_id", fk=true, fk_tablename="ingrediente_tipo")
	public Integer dgetIngredientes_tipo_id() {
		return ingredientes_tipo_id;
	}

	public void dsetIngredientes_tipo_id(Integer ingredientes_tipo_id) {
		this.ingredientes_tipo_id = ingredientes_tipo_id;
	}
	
	@Entity(attributeName = "ingrediente_armazenamentos_id", fk=true, fk_tablename="ingrediente_armazenamentos")
	public Integer dgetIngrediente_armazenamentos_id() {
		return ingrediente_armazenamentos_id;
	}

	public void dsetIngrediente_armazenamentos_id(Integer ingrediente_armazenamentos_id) {
		this.ingrediente_armazenamentos_id = ingrediente_armazenamentos_id;
	}	
	
	
}
