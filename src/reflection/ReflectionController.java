package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import exceptions.NoActionException;
import interfaces.IController;
import jsonclasses.JReturn;
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
	
}
