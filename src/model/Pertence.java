package model;

import annotations.AModelClasses;
import annotations.Entity;
import jsonclasses.JReturn;

@AModelClasses(needUserObject = false)
public class Pertence extends Model{
	
	private Ingredientes ingrediente;
	private Receita receita;
	private Integer quantidade;
	private Integer id;
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "pertence";
	}
	
	@Entity(attributeName = "ingredientes_id", fk=true)
	public Ingredientes dgetIngrediente() {
		return ingrediente;
	}
	public void dsetIngrediente(Ingredientes ingrediente) {
		this.ingrediente = ingrediente;
	}
	
	@Entity(attributeName="receitas_id", fk=true, isMapped=true)
	public Receita dgetReceita() {
		return receita;
	}
	public void dsetReceita(Receita receita) {
		this.receita = receita;
	}
	
	@Entity(attributeName="quantidade")
	public Integer dgetQuantidade() {
		return quantidade;
	}
	public void dsetQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Entity(attributeName = "id", pk=true)
	public Integer dgetId() {
		return id;
	}
	public void dsetId(Integer id) {
		this.id = id;
	}

	@Override
	public void verify(JReturn r) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

