package model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import annotations.Entity;
import jresponseclasses.JReturn;

public class EstoqueParent extends Model {

	Integer id;
	
	User user;
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

	@Entity(attributeName = "usuario_id", fk=true)
	public User dgetUser() {
		return user;
	}

	public void dsetUser(User user) {
		this.user = user;
	}
	
	@Entity(attributeName = "quantidade")
	public Integer dgetQuantidade() {
		return quantidade;
	}

	public void dsetQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Entity(attributeName = "data_insercao", required=false)
	public Date dgetDataInsercao() {
		return dataInsercao;
	}

	public void dsetDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}

	@Entity(attributeName = "data_retirada",required=false)
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

	@Override
	public void verify(JReturn r) {
		
		//Set actual date when verifying
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d = new java.util.Date();
		Date date = new Date(d.getYear(), d.getMonth(), d.getDate());
		System.out.println(date); 
		dsetDataInsercao(date);
		
		if(quantidade < 0){
			r.addSimpleError("31");//Quantity can't be lower than 0
		}
		
		
		
	}

}
