package model;

import annotations.AModelClasses;
import annotations.AEntity;
import jresponseclasses.JReturn;

@AModelClasses(needUserObject = false)
public class ReceitaRendimentoTipos extends Model {

	Integer id;
	String tipo;
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "receita_rendimento_tipos";
	}

	@AEntity(attributeName = "id", pk=true)
	public Integer dgetId() {
		return id;
	}

	public void dsetId(Integer id) {
		this.id = id;
	}

	@AEntity(attributeName = "tipo")
	public String dgetTipo() {
		return tipo;
	}

	public void dsetTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public void verify(JReturn r) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
