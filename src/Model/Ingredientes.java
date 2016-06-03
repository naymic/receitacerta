package Model;

import Reflection.Entity;
import Utils.Return;

public class Ingredientes extends Model{

	private Integer id;
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
		// TODO Auto-generated method stub
		return null;
	}
	
	@Entity(attributeName = "id", pk=true)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Entity(attributeName = "calorias")
	public Double getCalorias() {
		return calorias;
	}

	public void setCalorias(Double calorias) {
		this.calorias = calorias;
	}
	
	@Entity(attributeName = "ingredientes_unidade_id1", fk=true, fk_tablename="ingrediente_unidades")
	public Integer getIngredientes_unidade_id1() {
		return ingredientes_unidade_id1;
	}

	public void setIngredientes_unidade_id1(Integer ingredientes_unidade_id1) {
		this.ingredientes_unidade_id1 = ingredientes_unidade_id1;
	}
	
	@Entity(attributeName = "ingredientes_tipo_id", fk=true, fk_tablename="ingrediente_tipo")
	public Integer getIngredientes_tipo_id() {
		return ingredientes_tipo_id;
	}

	public void setIngredientes_tipo_id(Integer ingredientes_tipo_id) {
		this.ingredientes_tipo_id = ingredientes_tipo_id;
	}
	
	@Entity(attributeName = "ingrediente_armazenamentos_id", fk=true, fk_tablename="ingrediente_armazenamentos")
	public Integer getIngrediente_armazenamentos_id() {
		return ingrediente_armazenamentos_id;
	}

	public void setIngrediente_armazenamentos_id(Integer ingrediente_armazenamentos_id) {
		this.ingrediente_armazenamentos_id = ingrediente_armazenamentos_id;
	}
	
	
	
}
