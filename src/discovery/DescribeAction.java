package discovery;

import java.lang.reflect.Method;

import annotations.AControllerMethod;
import exceptions.NoActionException;
import model.Describe;
import reflection.ReflectionController;
import reflection.ReflectionAnnotation;

public class DescribeAction extends Describe{
	


	public DescribeAction(ReflectionController rc, Method actionMethod){
		
		int i = actionMethod.getName().indexOf("Action");
		this.dsetName(actionMethod.getName().substring(0, i));
				
		AControllerMethod acm = actionMethod.getAnnotation(AControllerMethod.class);
		ReflectionAnnotation ra = new ReflectionAnnotation(acm);
		this.setAnnotations(ra.getAnnotationMap());
	}
		
	
	
	
	
}
