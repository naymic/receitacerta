package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Entity;

public class ReflectionAnnotation extends GenericReflection {

	
	
	public ReflectionAnnotation(Object object) {
		super(object);
		
	}
	
	public ArrayList<Method> getAnnotationMethods(){
		
		Method[] methods = this.getObject().getClass().getDeclaredMethods();
		ArrayList<Method> list = new ArrayList<>();
		
		for(Method method : methods){
			if(method.getName() != "equals" && method.getName() != "toString" && method.getName() != "hashCode" && method.getName() != "annotationType"){
				list.add(method);
			}
		}

		return list;
	}
	
	public HashMap<String, Object> getAnnotationMap(){
		HashMap<String, Object> map = new HashMap<>();
		ArrayList<Method> methodList = this.getAnnotationMethods();
		
		for(Method method : methodList){
			map.put(method.getName(), this.getMethodValue(method));
		}
		
		
		return map;
		
 	}

}
