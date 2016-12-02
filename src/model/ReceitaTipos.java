package model;

import annotations.AModelClasses;
import annotations.AEntity;
import jresponseclasses.JReturn;

@AModelClasses(needUserObject = false)
public class ReceitaTipos extends Model {

	private Integer id;
	private String nome;
	
	@AEntity(attributeName = "id", pk=true)
	public Integer dgetId() {
		return id;
	}

	public void dsetId(Integer id) {
		this.id = id;
	}
	
	@AEntity(attributeName = "nome")
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

	@Override
	public void verify(JReturn r) {
		// TODO Auto-generated method stub
		
	}

}
