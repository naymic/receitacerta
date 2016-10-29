package discovery;

import java.lang.reflect.Method;

import annotations.AControllerMethod;
import exceptions.NoActionException;
import reflection.ReflectionController;

public class DescribeAction {
	

	String actionName;
	AControllerMethod aMethod;

	public DescribeAction(ReflectionController rc, String actionName){
		this.setActionName(actionName);
		Method m = null;
		try {
			m = rc.getAction(actionName);
		} catch (NoActionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setaMethod(ReflectionController.getAControllerMethod(m));
	}
		
	public AControllerMethod getaMethod() {
		return aMethod;
	}

	public void setaMethod(AControllerMethod aMethod) {
		this.aMethod = aMethod;
	}
	
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	
}
