package model;

import annotations.Entity;

public class Pertence {
	
	private Ingredientes ingrediente;
	private Receita receita;
	private Integer quantidade;
	private Integer id;
	
	@Entity(attributeName = "ingrediente", fk=true)
	public Ingredientes getIngrediente() {
		return ingrediente;
	}
	public void setIngrediente(Ingredientes ingrediente) {
		this.ingrediente = ingrediente;
	}
	
	@Entity(attributeName="receita_id", fk=true, isMapped=true)
	public Receita getReceita() {
		return receita;
	}
	public void setReceita(Receita receita) {
		this.receita = receita;
	}
	
	@Entity(attributeName="quantidade")
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Entity(attributeName = "id", pk=true)
	public Integer dgetId() {
		return id;
	}
	public void dsetId(Integer id) {
		this.id = id;
	}
}

