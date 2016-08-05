package Model;

import Annotations.Entity;

public class IngredienteUnidades extends Model{
	
	private Integer id;
	private String nomeUnidade;
	private String descricao;
	
	@Override
	public String getTableName() {
		return "ingrediente_unidades";
	}

	@Entity(attributeName = "id", pk=true)
	public Integer dgetId() {
		return id;
	}

	public void dsetId(Integer id) {
		this.id = id;
	}

	@Entity(attributeName = "nome_unidade")
	public String dgetNomeUnidade() {
		return nomeUnidade;
	}

	
	public void dsetNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}

	@Entity(attributeName = "descricao", required=false)
	public String dgetDescricao() {
		return descricao;
	}

	
	public void dsetDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
	
}
