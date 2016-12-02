package model;

import annotations.AModelClasses;
import annotations.AEntity;
import jresponseclasses.JReturn;

@AModelClasses(needUserObject = false)
public class IngredienteTipo extends Model{

	private Integer id;
	private String nomeTipo;
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "ingrediente_tipo";
	}


	@AEntity(attributeName = "id", pk=true)
	public Integer dgetId() {
		return id;
	}


	public void dsetId(Integer id) {
		this.id = id;
	}

	@AEntity(attributeName = "nome_tipo")
	public String dgetNomeTipo() {
		return nomeTipo;
	}

	
	public void dsetNomeTipo(String nomeTipo) {
		this.nomeTipo = nomeTipo;
	}


	@Override
	public void verify(JReturn r) {
		// TODO Auto-generated method stub
		
	}

}
