package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import annotations.AControllerMethod;
import annotations.AModelClasses;
import exceptions.NoActionException;
import interfaces.IController;
import jresponseclasses.JReturn;
import model.Model;

public class ReflectionController extends GenericReflection{
	
	public ReflectionController(IController object) {
		super(object);
	}

	public Method getAction(String actionName)throws NoActionException{
		
		if(!actionName.endsWith("Action")){
			actionName += "Action";
		}
		Method m = null;
		
		try{
		m = this.getMethod(actionName, JReturn.class, Model.class);		
		}catch(RuntimeException e){
			throw new NoActionException(getObjectClass().getSimpleName(), actionName);
		}
		return m; 
		
	}
	
	public void executeAction(IController controller, Method actionMethod, JReturn r, Model object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		actionMethod.invoke(controller, r, object);
	}
	
	
	public static AControllerMethod getAControllerMethod(Method m)throws RuntimeException{
		
		try{
			AControllerMethod acm = m.getAnnotation(AControllerMethod.class);
			return acm;
			
		}catch(NullPointerException npe){
			System.out.println(npe.getMessage());
			throw new RuntimeException(npe.getMessage());
		}
			
	}
	
	
	public ArrayList<Method> getControllerActions(){
		Method[] methodVector = this.getObjectClass().getMethods();
		ArrayList<Method> actionList = new ArrayList<>();
		
		for(Method method : methodVector){
			if(method.isAnnotationPresent(AControllerMethod.class)){
				actionList.add(method);
			}
		}
		
		return actionList;
	}
	

	
}
