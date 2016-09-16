package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import annotations.AControllerMethod;
import converters.GenericConverter;
import dao.DAO;
import dao.DAORelation;
import dao.DAORelationList;
import enums.ReturnType;
import exceptions.NoActionException;
import jresponseclasses.JData;
import jresponseclasses.JReturn;
import model.Model;
import reflection.ReflectionDAO;
import reflection.ReflectionDAORelation;
import utils.StringUtils;
import utils.Transform;

public class CrudController extends GenericController{
	
	
	
	/**
	 * Method for "novo" retrieves data for a new object
	 * fill map with infos for the map
	 * @param r 
	 * @param object
	 * @return			String	JSON string to print on view
	 */
	@AControllerMethod(checkAttributes = false)
	public JReturn novoAction(JReturn r, Model object){

		ReflectionDAO rdr = new ReflectionDAO(object);
		
		//Add Object to the Return Data
		r.getData().setDataObject(object);
		
		//Add all FK Lists of the object for select options
		ArrayList<ArrayList<Model>> jdataList = DAO.getInstance().selectForForm(object);
		//Add all Form dataTypes
		r.getData().setDataTypes(rdr.getArrayFields());
		r.setReturnType(ReturnType.FORM);
		return r;		
	}
	
	/**
	 * Method for "edit" -> retrieves data of an existent object ready to edit
	 * @param r
	 * @param object
	 * @return			String	JSON string to print on view
	 */
	@AControllerMethod(checkAttributes = false, checkPK = true)
	public JReturn editAction( JReturn r, Model object){

		//Check if PK is set or not
		ReflectionDAORelation rdr = new ReflectionDAORelation(object);

		List<Model> list = new ArrayList<>();
		if(r.isSuccess()){
			list = DAORelation.getInstance().select(object);
		}

		if(r.isSuccess() && !list.isEmpty()){
			object = list.get(0);

			//Add Object to the Return Data
			r.getData().setDataObject(object);

			//Add all FK Lists of the object for select options
			ArrayList<ArrayList<Model>> jdataList = DAO.getInstance().selectForForm(object);
			//Add all Form dataTypes
			r.getData().setDataTypes(rdr.getArrayFields());

			//Set JSon response return type
			r.setReturnType(ReturnType.FORM);
		}else{
			r.addSimpleError("Data for "+ object.getClass().getSimpleName() +" not found!");
			return r;
		}

		return r;
	}
	
	/**
	 * Method for "salvar" -> save insert or update a row in database
	 * @param r
	 * @param object
	 * @return			String	JSON string to print on view
	 */
	@AControllerMethod(checkAttributes = true)
	public JReturn salvarAction(JReturn r, Model object){
		
		object.verify(r);
		
		if(r.isSuccess())
			r = DAO.getInstance().save(object, r);
		
		if(r.isSuccess()){
			r.addMsg("Data "+ object.getClass().getSimpleName() +" successfully saved in database");
		}else{
			r.addSimpleError("Data "+ object.getClass().getSimpleName() +" could not be saved in database");
		}
		
		r.getData().setDataObject(object);
		
		
		return r;
	}
	
	/**
	 * Method for "remover" -> remove a row in database successfully could not
	 * @param r
	 * @param object
	 * @return			String	JSON string to print on view
	 */
	@AControllerMethod(checkAttributes = false, checkPK = true)
	public JReturn removerAction(JReturn r, Model object){

		if(r.isSuccess())
			r = DAO.getInstance().delete(object, r);
		
		if(r.isSuccess()){
			r.addMsg("Data "+ object.getClass().getSimpleName() +" successfully removed");
		}else{
			r.addSimpleError("Data "+ object.getClass().getSimpleName() +" could not be removed");
		}

		return r;
	}
	
	/**
	 * Method for "busca" -> select rows in database
	 * @param r
	 * @param object
	 * @return			String	JSON string to print on view
	 */
	@AControllerMethod(checkAttributes = false)
	public JReturn buscaAction(JReturn r, Model object){
		List<Model> list =null;
		
		list = DAORelation.getInstance().select(object);
		
		this.selectObjectCheck(r, list, object);
		
		r.getData().setDataList(list);
		r.setReturnType(ReturnType.SELECT);
		return r;
	}
	
	
	/**
	 * Method for advanced "busca" -> select rows in database
	 * Advanced means that the WHERE SQL params are LIKE and not =
	 * @param r
	 * @param object
	 * @return			String	JSON string to print on view
	 */
	@AControllerMethod(checkAttributes = false)
	public JReturn buscaavancadaAction(JReturn r, Model object){
		List<Model> list =null;
		
		list = DAORelation.getInstance().search(object);
		
		this.selectObjectCheck(r, list, object);
		
		JData jd =  new JData(this.getObject().getClassName());
		r.getData().setDataList(list);
		r.setReturnType(ReturnType.SEARCH);	

		return r;
	}
	
	
	/**
	 * Checks if data was found  for in a select/search reqeust
	 * @param r
	 * @param list
	 * @param object
	 */
	private void selectObjectCheck(JReturn r, List<Model> list, Model object){
		if(list != null && !list.equals(null) && list.size() > 0){
			object = (Model) list.get(0); 
		}else{
			r.addMsg("No data found with params: "+ StringUtils.searchString(object));	
		}
	}
	
	
	private ArrayList<JData> prepareFormData(ArrayList<ArrayList<Model>> list){
		ArrayList<JData> jdataList = new ArrayList<>();
		Iterator<ArrayList<Model>> itList = list.iterator();
		
		while(itList.hasNext()){
			ArrayList<Model> modelList = itList.next();
			JData jd= new JData();
			jd.setDataList(modelList);
			jdataList.add(jd);
		}
		
		
		return jdataList;
	}
	
	

	
	@Override
	public boolean needAuthentication(){
		return true;
	}

}
