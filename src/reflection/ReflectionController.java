package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import annotations.AControllerMethod;
import annotations.AModelClasses;
import exceptions.NoActionException;
import interfaces.IController;
import jresponseclasses.JReturn;
import model.Model;

public class ReflectionController extends GenericReflection{
	
	public ReflectionController(Object object) {
		super(object);
	}

	public Method getAction(String usecase, String action)throws NoActionException{
		
		String actionName = action + "Action";
		Method m = null;
		
		try{
		m = this.getMethod(actionName, JReturn.class, Model.class);		
		}catch(RuntimeException e){
			throw new NoActionException(usecase, actionName);
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
	

	
}
