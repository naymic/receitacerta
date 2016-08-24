package dao;

import java.lang.reflect.Method;
import java.util.ArrayList;

import model.Model;
import reflection.GenericReflection;
import reflection.ReflectionDAO;
import reflection.ReflectionDAORelationList;

public class DAORelationList extends DAORelation {
	
	private static DAORelationList dr = null;
	
	public static DAORelationList getInstance(){
		if(dr == null){
			dr = new DAORelationList();
		}
		return dr;
	}
	
	protected DAORelationList(){
		super();	
	};
	
	
	public ArrayList<Model> select(Model object){		
		return select(object, 1);
		
	}
	
	public ArrayList<Model> select(Model object, int deepness){
		ArrayList<Model> modelList = DAORelation.getInstance().select(object);
		
		for(int i = 0; i < modelList.size(); i++){
			Model m = modelList.get(i);
			modelList.set(i, this.selectMappedLists(m, deepness));
		}
		
		
		return modelList;
		
	}
	
	/**
	 * Find array attributes of a model and select its content
	 * @param mappdedObject
	 * @return Model Object that has now its 1..N relation lists set
	 */
	private Model selectMappedLists(Model mappdedObject, int deepness){
		ReflectionDAORelationList rdrl = new ReflectionDAORelationList(mappdedObject);
		ArrayList<Method> mappedList = rdrl.getGetArray();
		Model object = null;
		
		for(Method m : mappedList){
		Object obj = rdrl.getMethodValue(m); 
			if(obj.getClass().isArray()){
				//Instance a object and set the mapped Object in 
				object = (Model) GenericReflection.instanciateObjectByName(obj.getClass().getComponentType());
				ReflectionDAORelationList.setMappedObject(mappdedObject, object);
				
				//Select all objects that combine with the one object set of the array
				ArrayList<Model> attributeList = new ArrayList<>();
				if(deepness > 1){
					attributeList = DAORelationList.getInstance().select(object,deepness-1);
				}else{
					attributeList = DAORelation.getInstance().select(object);
				}
				
				rdrl.setMethodValue(m, attributeList);
			}
		}
		
		return mappdedObject;
	}
	
}
