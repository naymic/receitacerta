package model;

import annotations.Entity;

public class ReceitaView extends Receita{
	
	private Double caloriasTotal;
	
	@Entity(attributeName="total_cal")
	public Double dgetCaloriasTotal() {
		return caloriasTotal;
	}

	public void dsetCaloriasTotal(Double caloriasTotal) {
		this.caloriasTotal = caloriasTotal;
	}	
	
}
