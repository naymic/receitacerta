package Model;

import Reflection.Entity;

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

}
