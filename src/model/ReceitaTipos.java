package model;

import annotations.Entity;

public class ReceitaTipos extends Model {

	private Integer id;
	private String nome;
	
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

	@Override
	public String getTableName() {
		return "receita_tipos";
	}

}