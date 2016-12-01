package model;

import annotations.AModelClasses;
import annotations.AEntity;
import jresponseclasses.JReturn;

@AModelClasses(needUserObject = false)
public class Passo extends Model{
	private Integer id;
	private ReceitaView receita;
	private Integer numeroPasso;
	private String texto;
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "receita_passos";
	}
	
	@AEntity(attributeName="id", pk=true)
	public Integer dgetId() {
		return id;
	}
	public void dsetId(Integer id) {
		this.id = id;
	}
	
	@AEntity(attributeName="receitas_id", fk=true, isMapped=true )
	public ReceitaView dgetReceita() {
		return receita;
	}
	public void dsetReceita(ReceitaView receita) {
		this.receita = receita;
	}
	
	@AEntity(attributeName="nr_passo")
	public Integer dgetNumeroPasso() {
		return numeroPasso;
	}
	public void dsetNumeroPasso(Integer numeroPasso) {
		this.numeroPasso = numeroPasso;
	}
	
	@AEntity(attributeName="texto")
	public String dgetTexto() {
		return texto;
	}
	public void dsetTexto(String texto) {
		this.texto = texto;
	}

	@Override
	public void verify(JReturn r) {
		// TODO Auto-generated method stub
		
	}
	
	
}
