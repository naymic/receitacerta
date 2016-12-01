package model;

import annotations.AModelClasses;
import annotations.AEntity;
import jresponseclasses.JReturn;

@AModelClasses(needUserObject = false)
public class Pertence extends Model{
	
	private Ingredientes ingrediente;
	private ReceitaView receita;
	private Integer quantidade;
	private Integer id;
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "pertence";
	}
	
	@AEntity(attributeName = "ingredientes_id", fk=true)
	public Ingredientes dgetIngrediente() {
		return ingrediente;
	}
	public void dsetIngrediente(Ingredientes ingrediente) {
		this.ingrediente = ingrediente;
	}
	
	@AEntity(attributeName="receitas_id", fk=true, isMapped=true)
	public ReceitaView dgetReceita() {
		return receita;
	}
	public void dsetReceita(ReceitaView receita) {
		this.receita = receita;
	}
	
	@AEntity(attributeName="quantidade")
	public Integer dgetQuantidade() {
		return quantidade;
	}
	public void dsetQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@AEntity(attributeName = "id", pk=true)
	public Integer dgetId() {
		return id;
	}
	public void dsetId(Integer id) {
		this.id = id;
	}

	@Override
	public void verify(JReturn r) {
		// TODO Auto-generated method stub
		if(this.dgetQuantidade() < 1){
			r.addAttributeError("Pertence", "quantidade", "31");//The quantity have to be more than 0
		}
	}
	
	
	
}

