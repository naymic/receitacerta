package model;

import java.util.ArrayList;

import annotations.AModelClasses;
import annotations.Entity;
import jresponseclasses.JReturn;

@AModelClasses(needUserObject = true)
public class ReceitaView extends ReceitaParent{
	

	private String receitaTiposNome;
	private String receitaRendimentoTipos;
	private Double maxCalories;
	private ArrayList<Pertence> listaPertence;
	private ArrayList<Passo> listaPassos;
	
	
	public ReceitaView(){
		ArrayList<Passo> passoList = new ArrayList<>();
		passoList.add(new Passo());
		this.asetListaPassos(passoList);
		
		ArrayList<Pertence> pertenceList = new ArrayList<>();
		pertenceList.add(new Pertence());
		this.asetListaPertence(pertenceList);
	}
	
	@Override
	public void verify(JReturn r) {
		// TODO Auto-generated method stub
		
	}
	
	public String getTableName() {
		return "view_receitas";
	}
	
	@Entity(attributeName="receita_tipos_id")
	public String dgetReceitaTiposNome() {
		return receitaTiposNome;
	}

	public void dsetReceitaTiposNome(String receitaTiposNome) {
		this.receitaTiposNome = receitaTiposNome;
	}

	@Entity(attributeName="receita_rendimento_tipos_id")
	public String dgetReceitaRendimentoTipos() {
		return receitaRendimentoTipos;
	}

	public void dsetReceitaRendimentoTipos(String receitaRendimentoTipos) {
		this.receitaRendimentoTipos = receitaRendimentoTipos;
	}
	
	@Entity(attributeName="total_cal", required=false)
	public Double dgetMaxCalories() {
		return maxCalories;
	}

	public void dsetMaxCalories(Double max_calories) {
		this.maxCalories = max_calories;
	}	
	
	@Entity(attributeName="Pertence", fk=true)
	public ArrayList<Pertence> agetListaPertence() {
		return listaPertence;
	}

	public void asetListaPertence(ArrayList<Pertence> listaPertence) {
		this.listaPertence = listaPertence;
	}

	@Entity(attributeName="Passo", fk=true)
	public ArrayList<Passo> agetListaPassos() {
		return listaPassos;
	}

	public void asetListaPassos(ArrayList<Passo> listaPassos) {
		this.listaPassos = listaPassos;
	}

	
	
}
