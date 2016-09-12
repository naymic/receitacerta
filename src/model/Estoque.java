package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import annotations.Entity;
import jsonclasses.JReturn;

/***
 * 
 * @author micha
 *
 * Class to know the personal foodstock at home
 *
 */
public class Estoque extends Model {
	
	
	Integer id;
	Ingredientes ingredientesId;
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
	
	@Entity(attributeName="ingredientes_id", fk=true)
	public Ingredientes getIngredientesId() {
		return ingredientesId;
	}

	public void setIngredientesId(Ingredientes ingredientesId) {
		this.ingredientesId = ingredientesId;
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

	@Override
	public void verify(JReturn r) {
		Date d = new Date();
		
		//Set actual date
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date)); //2014-08-06
		dsetDataInsercao(d);
		
		if(quantidade < 0){
			r.addSimpleError("Quantity can't be lower than 0");
		}
		
		
		
	}

	

	
	
	
	
	

}
