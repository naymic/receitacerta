package model;

import java.util.ArrayList;

import annotations.AModelClasses;
import annotations.Entity;
import jresponseclasses.JReturn;

@AModelClasses(needUserObject = true)
public class Receita extends ReceitaParent{
	
	private ReceitaTipos receitaTipos;
	private ReceitaRendimentoTipos receitaRendimentoTipos;
	//private ArrayList<Pertence> listaPertence;
	//private ArrayList<Passo> listaPassos;
	private Double maxCalories;
	private String stringIngredientesId;
	private Ingredientes ingredientes;

	@Override
	public String getTableName() {
		return "receitas";
	}
	
	public Receita(){
		
		
	}

	@Entity(attributeName = "receita_tipos_id", fk=true)
	public ReceitaTipos dgetReceitaTipos() {
		return receitaTipos;
	}

	public void dsetReceitaTipos(ReceitaTipos receitaTipos) {
		this.receitaTipos = receitaTipos;
	}
	
	@Entity(attributeName = "receita_rendimento_tipos_id", fk=true)
	public ReceitaRendimentoTipos dgetReceitaRendimentoTipos() {
		return receitaRendimentoTipos;
	}

	public void dsetReceitaRendimentoTipos(ReceitaRendimentoTipos receitaRendimentoTipos) {
		this.receitaRendimentoTipos = receitaRendimentoTipos;
	}
	
	@Entity(attributeName="", required=false)
	public Double getMaxCalories() {
		return maxCalories;
	}

	public void setMaxCalories(Double max_calories) {
		this.maxCalories = max_calories;
	}
	
	//Not a attribute in the table, but used to get the ingredients list
		@Entity(attributeName="")
		public String getStringIngredientesId() {
			return stringIngredientesId;
		}

		public void setStringIngredientesId(String stringIngredientesId) {
			this.stringIngredientesId = stringIngredientesId;
		}


	@Override
	public void verify(JReturn r) {
		// TODO Auto-generated method stub
		
	}

	

}
