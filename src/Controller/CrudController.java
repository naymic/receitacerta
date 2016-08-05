package Controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Converter.GenericConverter;
import Enums.ReturnType;
import GenericDao.DAO;
import GenericDao.DAORelation;
import JsonClasses.JData;
import JsonClasses.JReturn;
import Model.Model;
import Reflection.ReflectionDAO;
import Reflection.ReflectionDAORelation;
import Utils.JSON;
import Utils.StringUtils;

public class CrudController extends GenericController{


	@Override
	public void execute(JReturn r, String action){
		super.execute(r, action);
		
		String json = "";
		Model object;
		
	
			object = this.initObj(r);
			
			if(!r.isSuccess()){
				JSON j = new JSON();
				json = j.returnConstruct(r);
			}else  if(action.equalsIgnoreCase("buscaavancada")){
				json = this.selectObjectSearch(r, object);

			}if(action.equalsIgnoreCase("busca")){
				json = this.selectObject(r, object);

			}else if(action.equalsIgnoreCase("novo")){
				json = this.newObject(object);

			}else if(action.equalsIgnoreCase("edit")){
				json = this.editObject(r, object);

			}else if(action.equalsIgnoreCase("salvar")){
				object.verify(r);
				if(!r.isSuccess()){
					JSON j = new JSON();
					json = j.returnConstruct(r);
				}else{
					json = this.saveObject(r, object);
				}
			}else if(action.equalsIgnoreCase("remover")){
				json = this.removeObject(r, object);

			}


			this.setJson(json);		
	}
	

	@Override
	/**
	 * Gets the list of valid actions for this controller
	 */
	public List<String> getValidActionsList() {
		validActions = new ArrayList<String>();
		
		
		//Busca normal
		validActions.add("busca");
		
		//Busca avançada
		validActions.add("buscaavancada");
		
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
	public String editObject( JReturn r, Model object){
		JSON j = new JSON();
		
		//Check if PK is set or not
		ReflectionDAORelation rdr = new ReflectionDAORelation(object);
		if(rdr.getPK() == null){
			r.addSimpleError("Primary key is not set. Object "+ object.getClass().getSimpleName() +" not found!");
			return j.returnConstruct(r);
		}
		
		List<Model> list = DAORelation.getTestInstance().select(object);
		if(!list.isEmpty()){
			object = list.get(0);
		}else{
			r.addSimpleError("Data for "+ object.getClass().getSimpleName() +" not found!");
			return j.returnConstruct(r);
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
	public String saveObject(JReturn r, Model object){
		
		r = DAO.getInstance().save(object, r);
		if(r.isSuccess()){
			r.addMsg("Data "+ object.getClass().getSimpleName() +" successfully saved in database");
		}else{
			r.addSimpleError("Data "+ object.getClass().getSimpleName() +" could not be saved in database");
		}
		
		JSON j = new JSON();
		return j.returnConstruct(r);
	}
	
	/**
	 * Method for "remover" -> remove a row in database successfully could not
	 * @param r
	 * @param object
	 * @return			String	JSON string to print on view
	 */
	public String removeObject(JReturn r, Model object){
		r = DAO.getInstance().delete(object, r);
		if(r.isSuccess()){
			r.addMsg("Data "+ object.getClass().getSimpleName() +" successfully removed");
		}else{
			r.addSimpleError("Data "+ object.getClass().getSimpleName() +" could not be removed");
		}
		
		JSON j = new JSON();
		return j.returnConstruct(r);
	}
	
	/**
	 * Method for "busca" -> select rows in database
	 * @param r
	 * @param object
	 * @return			String	JSON string to print on view
	 */
	public String selectObject(JReturn r, Model object){
		List<Model> list =null;
		
		list = DAORelation.getInstance().select(object);
		
		JSON j = new JSON();
		this.selectObjectCheck(r, list, object);
		r.setReturnType(ReturnType.SEARCH);
		
		r.getData().setData((ArrayList<Model>)list);
		return j.returnConstruct(r);
	}
	
	
	/**
	 * Method for advanced "busca" -> select rows in database
	 * Advanced means that the WHERE SQL params are LIKE and not =
	 * @param r
	 * @param object
	 * @return			String	JSON string to print on view
	 */
	public String selectObjectSearch(JReturn r, Model object){
		List<Model> list =null;
		
		list = DAORelation.getInstance().search(object);
		
		
		JSON j = new JSON();
		this.selectObjectCheck(r, list, object);
		
		JData jd =  new JData();
		jd.setData((ArrayList<Model>)list);
		r.setData(jd);
		return j.returnConstruct(r);
	}
	
	
	
	public void selectObjectCheck(JReturn r, List<Model> list, Model object){
		if(list.size() > 0){
			object = (Model) list.get(0); 
		}else{
			r.addMsg("No data found with params: "+ StringUtils.searchString(object));	
		}
	}

	
	@Override
	public boolean needAuthentication(){
		return true;
	}


}
