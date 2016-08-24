package model;

import annotations.Entity;

public class Passo {
	private Integer id;
	private Receita receita;
	private Integer numeroPasso;
	private String texto;
	
	@Entity(attributeName="id", pk=true)
	public Integer dgetId() {
		return id;
	}
	public void dsetId(Integer id) {
		this.id = id;
	}
	
	@Entity(attributeName="receitas_id", fk=true, isMapped=true )
	public Receita getReceita() {
		return receita;
	}
	public void setReceita(Receita receita) {
		this.receita = receita;
	}
	
	@Entity(attributeName="nr_passo")
	public Integer getNumeroPasso() {
		return numeroPasso;
	}
	public void setNumeroPasso(Integer numeroPasso) {
		this.numeroPasso = numeroPasso;
	}
	
	@Entity(attributeName="texto")
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
}
