package model;

import annotations.AModelClasses;
import annotations.AEntity;
import jresponseclasses.JReturn;

@AModelClasses(needUserObject = false)
public class IngredienteUnidades extends Model{
	
	private Integer id;
	private String nomeUnidade;
	private String descricao;
	
	@Override
	public String getTableName() {
		return "ingrediente_unidades";
	}

	@AEntity(attributeName = "id", pk=true)
	public Integer dgetId() {
		return id;
	}

	public void dsetId(Integer id) {
		this.id = id;
	}

	@AEntity(attributeName = "nome_unidade")
	public String dgetNomeUnidade() {
		return nomeUnidade;
	}

	
	public void dsetNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}

	@AEntity(attributeName = "descricao", required=false)
	public String dgetDescricao() {
		return descricao;
	}

	
	public void dsetDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public void verify(JReturn r) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
