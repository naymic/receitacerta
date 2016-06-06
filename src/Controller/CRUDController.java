package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import GenericDao.DAO;
import Interfaces.IController;
import Model.Model;
import Utils.Return;

public class CRUDController extends GenericController{
	
	private HashMap<String, Object> attributemap;

	@Override
	public List<String> getValidActionsList() {
		validActions = new ArrayList<String>();
		
		
		//Busca normal
		validActions.add("busca");
		
		//Nova inserção (trazer as relações para select options)
		validActions.add("novo");
		
		//Nova inserção (trazer as relações para select options e os outros informações)
		validActions.add("edit");
		
		// Salvar um objeto no banco de dados seja para atualizr ou inserir 
		validActions.add("salvar");
		
		//Remover um objeto do banco de dado
		validActions.add("remover");
		
		return validActions;
	}
	
	
	
	
	public HashMap<String, String> newObject( Model object){
	
				
		return null;
		
	}
	
	public HashMap<String, String> editObject( Model object){

		
		return null;
	}
	
	public HashMap<String, String> listObject( Model object){

		
		return null;
	}
	
	
	public void saveObject(Return r, Model object){
		r = DAO.getInstance().save(object);
	}
	
	public void removeObject(Return r, Model object){
		r = DAO.getInstance().delete(object);
	}

	


}
