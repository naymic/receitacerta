package model;

import annotations.AModelClasses;
import annotations.AEntity;
import jresponseclasses.JReturn;


public abstract class IngredientesParent extends Model{
	
	private Integer id;
	private String nome;
	private Double calorias;
	
	@AEntity(attributeName = "id", pk=true)
	public Integer dgetId() {
		return id;
	}

	public void dsetId(Integer id) {
		this.id = id;
	}
	
	@AEntity(attributeName = "nome")
	public String dgetNome() {
		return nome;
	}

	public void dsetNome(String nome) {
		this.nome = nome;
	}
	
	@AEntity(attributeName = "calorias")
	public Double dgetCalorias() {
		return calorias;
	}

	public void dsetCalorias(Double calorias) {
		this.calorias = calorias;
	}
	
	
	@Override
	public void verify(JReturn r) {
		int compare = 0;
		
		if(this.dgetCalorias() != null)
			compare = Double.compare(this.dgetCalorias(), new Double(0.0));
		
		if(this.dgetCalorias() != null && compare < 0 ){
			r.addAttributeError(this.getClass().getSimpleName(), "calorias", "Attribute calorias cannot be negative: "+ this.dgetCalorias());
		}
	}

}
