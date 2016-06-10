package Controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import GenericDao.DAO;
import GenericDao.DAORelation;
import Interfaces.IController;
import Model.Ingredientes;
import Model.Model;
import Reflection.ReflectionDAO;
import Reflection.ReflectionDAORelation;
import Utils.JSON;
import Utils.Return;

public class CRUDController extends GenericController{


	@Override
	public void execute(Return r, String action){
		super.execute(r, action);
		Model object = this.initObj();
		String json = "";
		
		if(action.equalsIgnoreCase("busca")){
			json = this.selectObject(r, object);
	
		}else if(action.equalsIgnoreCase("novo")){
			json = this.newObject(object);
			
		}else if(action.equalsIgnoreCase("edit")){
			json = this.editObject(r, object);
		
		}else if(action.equalsIgnoreCase("salvar")){
			json = this.saveObject(r, object);
		
		}else if(action.equalsIgnoreCase("remover")){
			json = this.removeObject(r, object);
		
		}
		
		this.setUniqueJson(json);		
	}
	
	/**
	 * Creates a object and set values to attributes 
	 * @return Model object
	 */
	public Model initObj(){

		try{
			
			if(!this.getVariableMapper().containsKey("className") && this.getClassName().length() > 0)
				throw new Exception("No given className, the controller have to reveice a className from the view");
			
			//Add Model. before the classname if not exist
			if(!this.getVariableMapper().get("className").toString().contains("Model.")){
				this.getVariableMapper().put("className", "Model."+this.getVariableMapper().get("className"));
			}
			
		
			Iterator<String> it = this.getVariableKeys();
			String paramName;
			String className = (String) this.getVariableValue("className");
			Model obj = ReflectionDAO.instanciateObjectByName(className);	
			ReflectionDAORelation rd = new ReflectionDAORelation(obj);


			while(it.hasNext()){
				paramName = it.next();
				String[] pN = paramName.split("[.]");
				if(paramName.contains(obj.getClass().getSimpleName()+".") && this.getVariableValue(paramName).toString().length() > 0){
					rd.setValueFromAttributename(paramName.split("[.]")[1], this.getVariableValue(paramName));
				}
			}
			return obj;
			
		}catch(Exception e){
			e.printStackTrace();
		}


		return null;
	}

	/**
	 * Gets the className of the mapper
	 * @return
	 */
	public String getClassName() {
		return (String) this.getVariableValue("className");
	}
	

	@Override
	/**
	 * Gets the list of valid actions for this controller
	 */
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
	
	
	
	/**
	 * Method for "novo" retrieves data for a new object
	 * fill map with infos for the map
	 * @param r 
	 * @param object
	 * @return			String	JSON string to print on view
	 */
	public String newObject(Model object){
		String json = "";

		ReflectionDAORelation rdr = new ReflectionDAORelation(object);
		
		JSON j = new JSON();
		return j.objectJson(rdr, false);		
	}
	
	/**
	 * Method for "edit" -> retrieves data of an existent object ready to edit
	 * @param r
	 * @param object
	 * @return			String	JSON string to print on view
	 */
	public String editObject( Return r, Model object){
		JSON j = new JSON();
		
		//Check if PK is set or not
		ReflectionDAORelation rdr = new ReflectionDAORelation(object);
		if(rdr.getPK() == null){
			r.addSimpleError("Primary key is not set. Object "+ object.getClass().getSimpleName() +" not found!");
			return j.messageConstruct(r);
		}
		
		List<Model> list = DAORelation.getTestInstance().select(object);
		if(!list.isEmpty()){
			object = list.get(0);
		}else{
			r.addSimpleError("Data for "+ object.getClass().getSimpleName() +" not found!");
			return j.messageConstruct(r);
		}
		ReflectionDAORelation rdr1 = new ReflectionDAORelation(object);
		
		return j.objectJson(rdr1, true);
	}
	
	/**
	 * Method for "salvar" -> save insert or update a row in database
	 * @param r
	 * @param object
	 * @return			String	JSON string to print on view
	 */
	public String saveObject(Return r, Model object){
		System.out.println(((Ingredientes)object).dgetCalorias());
		System.out.println(((Ingredientes)object).dgetNome());
		
		r = DAO.getInstance().save(object);
		if(r.isSuccess()){
			r.addMsg("Data "+ object.getClass().getSimpleName() +" successfully saved in database");
		}else{
			r.addSimpleError("Data "+ object.getClass().getSimpleName() +" could not be saved in database");
		}
		
		JSON j = new JSON();
		return j.messageConstruct(r);
	}
	
	/**
	 * Method for "remover" -> remove a row in database successfully could not
	 * @param r
	 * @param object
	 * @return			String	JSON string to print on view
	 */
	public String removeObject(Return r, Model object){
		r = DAO.getInstance().delete(object);
		if(r.isSuccess()){
			r.addMsg("Data "+ object.getClass().getSimpleName() +" successfully removed");
		}else{
			r.addSimpleError("Data "+ object.getClass().getSimpleName() +" could not be removed");
		}
		
		JSON j = new JSON();
		return j.messageConstruct(r);
	}
	
	/**
	 * Method for "busca" -> select rows in database
	 * @param r
	 * @param object
	 * @return			String	JSON string to print on view
	 */
	public String selectObject(Return r, Model object){
		List<Model> list = DAORelation.getTestInstance().select(object);
		JSON j = new JSON();
		if(list.size() > 0){
			object = (Model) list.get(0); 
		}else{
			r.addMsg("No data found");	
		}
		
		return j.listConstruct(object.getClass().getSimpleName(), r, list);
		
	}
	

	


}
