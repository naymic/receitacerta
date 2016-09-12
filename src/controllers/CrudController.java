package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import converters.GenericConverter;
import dao.DAO;
import dao.DAORelation;
import dao.DAORelationList;
import enums.ReturnType;
import exceptions.NoActionException;
import jsonclasses.JData;
import jsonclasses.JReturn;
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
	public JReturn novoAction(JReturn r, Model object){

		ReflectionDAORelation rdr = new ReflectionDAORelation(object);
		
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
	public JReturn editAction( JReturn r, Model object){
		
		//Check if PK is set or not
		ReflectionDAORelation rdr = new ReflectionDAORelation(object);
		if(rdr.getPK() == null){
			r.addSimpleError("Primary key is not set. Object "+ object.getClass().getSimpleName() +" not found!");
			return r;
		}
		
		List<Model> list = DAORelation.getInstance().select(object);
		if(!list.isEmpty()){
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
	public JReturn removerAction(JReturn r, Model object){
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
	public JReturn buscaAction(JReturn r, Model object){
		List<Model> list =null;
		
		list = DAORelationList.getInstance().select(object);
		
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
	public JReturn buscaavancadaAction(JReturn r, Model object){
		List<Model> list =null;
		
		list = DAORelationList.getInstance().search(object);
		
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
		if(list.size() > 0){
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
		return false;
	}

}
