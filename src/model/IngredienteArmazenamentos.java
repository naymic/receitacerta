package model;

import annotations.AModelClasses;
import annotations.Entity;
import jsonclasses.JReturn;


public class IngredienteArmazenamentos extends Model{

	private Integer id;
	private String nomeArmazenamento;
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "ingrediente_armazenamentos";
	}


	@Entity(attributeName = "id", pk=true)
	public Integer dgetId() {
		return id;
	}

	
	public void dsetId(Integer id) {
		this.id = id;
	}

	@Entity(attributeName = "nome_armazenamento")
	public String dgetNomeArmazenamento() {
		return nomeArmazenamento;
	}

	
	public void dsetNomeArmazenamento(String nome_armazenamento) {
		this.nomeArmazenamento = nome_armazenamento;
	}


	@Override
	public void verify(JReturn r) {
		// TODO Auto-generated method stub
		
	}

}
