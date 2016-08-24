package model;

import java.util.ArrayList;
import java.util.Date;

import annotations.Entity;

/***
 * 
 * @author micha
 *
 * Class to know the personal foodstock at home
 *
 */
public class Estoque extends Model {
	
	
	Integer id;
	Usuario userId;
	Integer quantidade;
	Date dataInsercao;
	Date dataRetirada;
	Date validade;
	
	@Override
	public String getTableName() {
		return "estoque";
	}

	@Entity(attributeName = "id", pk=true)
	public Integer dgetId() {
		return id;
	}

	public void dsetId(Integer id) {
		this.id = id;
	}

	@Entity(attributeName= "usuario_id", fk=true, isMapped=true)
	public Usuario dgetUserId() {
		return userId;
	}
	
	public void dsetUserId(Usuario userId) {
		this.userId = userId;
	}
	
	@Entity(attributeName = "quantidade")
	public Integer dgetQuantidade() {
		return quantidade;
	}

	public void dsetQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Entity(attributeName = "data_insercao")
	public Date dgetDataInsercao() {
		return dataInsercao;
	}

	public void dsetDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}

	@Entity(attributeName = "data_retirada")
	public Date dgetDataRetirada() {
		return dataRetirada;
	}

	public void dsetDataRetirada(Date dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

	@Entity(attributeName = "validade")
	public Date dgetValidade() {
		return validade;
	}

	public void dsetValidade(Date validade) {
		this.validade = validade;
	}

	

	
	
	
	
	

}
