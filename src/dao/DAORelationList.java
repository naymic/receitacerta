package dao;

import java.lang.reflect.Method;
import java.util.ArrayList;

import model.Model;
import reflection.GenericReflection;
import reflection.ReflectionDAO;
import reflection.ReflectionDAORelationList;

public class DAORelationList extends DAORelation{
	
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
	
	@Override
	public ArrayList<Model> search(Model object, Integer page){		
		return search(object, 1, page);
	}
	

	public ArrayList<Model> search(Model object, int deepness, Integer page){
		ArrayList<Model> modelList = DAORelation.getInstance().search(object, page);
		ArrayList<SelectList> selectThreads = new ArrayList<>();
		
		for(int i = 0; i < modelList.size(); i++){
			SelectList sc = new SelectList(modelList.get(i), deepness);
			selectThreads.add(i,sc);
			
			//setModelMappedList(deepness, modelList, i);
		}
		
		utils.ThreadManager.checkAliveThreads(selectThreads);
			
			
		return modelList;
		
		
		
	}
	
	
	public ArrayList<Model> select(Model object){		
		return select(object, 1);
	}
	
	public ArrayList<Model> select(Model object, int deepness){
		ArrayList<Model> modelList = DAORelation.getInstance().select(object);
		ArrayList<SelectList> selectThreads = new ArrayList<>();
		
		for(int i = 0; i < modelList.size(); i++){
			SelectList sc = new SelectList(modelList.get(i), deepness);
			selectThreads.add(i,sc);
			
			//setModelMappedList(deepness, modelList, i);
		}
		
		utils.ThreadManager.checkAliveThreads(selectThreads);
			
			
		return modelList;
		
		
		
	}

	public void setModelMappedList(int deepness, ArrayList<Model> modelList, int i) {
		Model model = modelList.get(i);
		modelList.set(i, this.selectMappedLists(model, deepness));
	}
	
	/**
	 * Find array attributes of a model and select its content
	 * @param mainObject
	 * @return Model Object that has now its 1..N relation lists set
	 */
	private Model selectMappedLists(Model mainObject, int deepness){
		ReflectionDAORelationList rdrl = new ReflectionDAORelationList(mainObject);
		ArrayList<Method> arrayObjectMethods = rdrl.getGetArray();
		Model objectOfArray = null;
		
		for(Method method : arrayObjectMethods){
		Class<?> classOfObject = rdrl.getMethodValueClass(method); 
		ArrayList<Model> list = (ArrayList<Model>)rdrl.getMethodValue(method);
			if(classOfObject == ArrayList.class){
				//Instance a object and set the mapped Object in 
				Class<?> arrayListObjectClass = list.get(0).getClass();
				objectOfArray = (Model) GenericReflection.instanciateByClass(arrayListObjectClass);
				ReflectionDAORelationList.setMappedObject(mainObject, objectOfArray);
				
				//Select all objects that combine with the one object set of the array
				ArrayList<Model> objectsArray = new ArrayList<>();
				if(deepness > 1){
					objectsArray = DAORelationList.getInstance().select(objectOfArray,deepness-1);
				}else{
					objectsArray = DAORelation.getInstance().select(objectOfArray);
				}
				
				rdrl.setMethodValue(method, objectsArray);
			}
		}
		
		return rdrl.getObject();
	}

	
	public class SelectList extends Thread{
		private Model model;
		private int deepness;
	
		public SelectList(Model model, int deepness){
			this.model = model;
			this.deepness = deepness;
			start();
			
		}
		
		
		@SuppressWarnings("deprecation")
		public void run(){
			DAORelationList.getInstance().selectMappedLists(this.model, this.deepness);
			this.stop();
		}
		
	}
		
	
}
