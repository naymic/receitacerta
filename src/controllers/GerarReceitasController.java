package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dao.DAORelationList;
import exceptions.NoActionException;
import interfaces.IApplicationSession;
import interfaces.IController;
import interfaces.IUser;
import jsonclasses.JObject;
import jsonclasses.JReturn;
import model.Model;
import model.Receita;

public class GerarReceitasController extends GenericController{
	
	
	
	
	public JReturn buscaAction(JReturn r, Model object){
		
		//Remove the maxCalories value to filter after
		Receita receita = (Receita)object;
		Double maxC = receita.getCaloriasTotal();
		receita.setCaloriasTotal(null);
		
		ArrayList<Model> list = DAORelationList.getInstance().select(object);
		
		
		//Create a filter to check if the maxCalorias are less than given from user
		
		
		//Create a filter to check if all ingredients informed by the user exist in the repices 
		
		return r;
		
	}


	@Override
	public boolean needAuthentication() {
		// TODO Auto-generated method stub
		return false;
	}


	

	
}
