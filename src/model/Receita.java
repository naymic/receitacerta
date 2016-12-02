package model;

import java.util.ArrayList;

import annotations.AModelClasses;
import annotations.AEntity;
import jresponseclasses.JReturn;

@AModelClasses(needUserObject = true)
public class Receita extends ReceitaParent{

	private ReceitaTipos receitaTipos;
	private ReceitaRendimentoTipos receitaRendimentoTipos;
	//private ArrayList<Pertence> listaPertence;
	//private ArrayList<Passo> listaPassos;
	private Double maxCalories;

	@Override
	public String getTableName() {
		return "receitas";
	}

	public Receita(){


	}

	@AEntity(attributeName = "receita_tipos_id", fk=true)
	public ReceitaTipos dgetReceitaTipos() {
		return receitaTipos;
	}

	public void dsetReceitaTipos(ReceitaTipos receitaTipos) {
		this.receitaTipos = receitaTipos;
	}

	@AEntity(attributeName = "receita_rendimento_tipos_id", fk=true)
	public ReceitaRendimentoTipos dgetReceitaRendimentoTipos() {
		return receitaRendimentoTipos;
	}

	public void dsetReceitaRendimentoTipos(ReceitaRendimentoTipos receitaRendimentoTipos) {
		this.receitaRendimentoTipos = receitaRendimentoTipos;
	}

	@AEntity(attributeName="", required=false, isInDB=false)
	public Double getMaxCalories() {
		return maxCalories;
	}

	public void setMaxCalories(Double max_calories) {
		this.maxCalories = max_calories;
	}

	


	@Override
	public void verify(JReturn r) {
		// TODO Auto-generated method stub

	}



}
