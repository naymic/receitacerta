package discovery;

import java.lang.reflect.Method;

import annotations.AControllerMethod;
import exceptions.NoActionException;
import model.Describe;
import reflection.ReflectionController;

public class DescribeAction extends Describe{
	

	String aMethod;

	public DescribeAction(ReflectionController rc, Method actionMethod){
		this.dsetName(actionMethod.getName());
		this.setaMethod(ReflectionController.getAControllerMethod(actionMethod).toString());
	}
		
	public String getaMethod() {
		return aMethod;
	}

	public void setaMethod(String aMethod) {
		this.aMethod = aMethod;
	}
	
	
	
}
